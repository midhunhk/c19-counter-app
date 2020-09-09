package com.ae.apps.c19counter.data.business

import com.ae.apps.c19counter.data.COUNTRY_SUMMARY_URL
import com.ae.apps.c19counter.data.STATE_SUMMARY_URL
import com.ae.apps.c19counter.data.models.Count
import com.ae.apps.c19counter.data.models.Summary
import com.ae.apps.c19counter.data.models.SummaryType
import com.ae.apps.c19counter.data.services.NetworkService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class SummaryCounter {

    companion object {
        fun getInstance() = SummaryCounter()
    }

    fun retrieveSummary(sources: List<Summary>, caller: SummaryReader) {
        val service = NetworkService.newInstance()

        doAsync {
            val responseSummaries = mutableListOf<Summary>()
            sources.forEach {
                // Collect the endpoint for
                val url = when (it.type) {
                    SummaryType.STATE -> STATE_SUMMARY_URL
                    SummaryType.COUNTRY -> COUNTRY_SUMMARY_URL
                }
                val webResponse = service.doGetRequest(url + it.code)
                val jsonObject = JSONObject(webResponse!!)

                val summaryResponse = parseWebResponse(it, jsonObject)
                responseSummaries.add(summaryResponse)

                // Call back the invoker with the result
                uiThread {
                    caller.onSummaryRead(summaryResponse)
                }
            }
        }
    }

    private fun parseWebResponse(sourceSummary: Summary, json: JSONObject): Summary {
        val updatedAt = json.getString("updatedAt")
        val todayCount = Count(
            json.getInt("confirmedToday"),
            json.getInt("recoveredToday"),
            json.getInt("deceasedToday")
        )
        val totalCount = Count(
            json.getInt("confirmedTotal"),
            json.getInt("recoveredTotal"),
            json.getInt("deceasedTotal")
        )
        return Summary(sourceSummary.code, sourceSummary.type, updatedAt, todayCount, totalCount)
    }

}