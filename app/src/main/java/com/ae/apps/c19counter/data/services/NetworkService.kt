package com.ae.apps.c19counter.data.services

import okhttp3.OkHttpClient
import okhttp3.Request

public class NetworkService : WebService {

    val client: OkHttpClient by lazy { OkHttpClient() }

    companion object {
        fun newInstance() = NetworkService()
    }

    override fun doGetRequest(url: String): String? {
        val request: Request = Request.Builder()
            .url(url)
            .method("GET", null)
            .build();

        val response = client.newCall(request).execute()
        return response.body?.string();
    }

}