package com.ae.apps.c19counter.data.services

import okhttp3.OkHttpClient

interface WebService {
    fun doGetRequest(url: String): String?
}