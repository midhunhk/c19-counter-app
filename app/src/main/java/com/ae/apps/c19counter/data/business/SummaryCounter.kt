/*
 * MIT License
 *
 * Copyright (c) 2020 Midhun Harikumar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ae.apps.c19counter.data.business

import com.ae.apps.c19counter.data.COUNTRY_SUMMARY_URL
import com.ae.apps.c19counter.data.STATE_SUMMARY_URL
import com.ae.apps.c19counter.data.models.*
import com.ae.apps.c19counter.data.models.Summary
import com.ae.apps.c19counter.data.services.NetworkService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.lang.Exception

class SummaryCounter {

    companion object {
        fun getInstance() = SummaryCounter()
    }

    fun retrieveSummary(sources: List<Code>, caller: SummaryConsumer) {
        val service = NetworkService.newInstance()

        doAsync {
            val responseSummaries = mutableListOf<Summary>()
            sources.forEach { code ->
                val errorOccurred = "ERROR OCCURRED"
                // Collect the endpoint for
                val url = when (code.type) {
                    SummaryType.STATE -> STATE_SUMMARY_URL
                    SummaryType.COUNTRY -> COUNTRY_SUMMARY_URL
                    SummaryType.UNKNOWN -> errorOccurred
                }
                if (url == errorOccurred) {
                    uiThread { caller.onRequestError("Error occurred for ${it.toString()}") }
                } else {
                    val webResponse = service.doGetRequest(url + code.code)
                    //val webResponse = "{\"countryName\":\"India\",\"countryCode\":\"IN\",\"updatedAt\":\"2020-09-20T04:32:11.000Z\",\"confirmedToday\":0,\"deceasedToday\":0,\"recoveredToday\":0,\"confirmedTotal\":5214677,\"deceasedTotal\":84372,\"recoveredTotal\":4112551}"
                    val jsonObject = JSONObject(webResponse!!)

                    val summaryResponse = parseWebResponse(code, jsonObject)
                    responseSummaries.add(summaryResponse)

                    // Call back the invoker with the result
                    uiThread {
                        if(summaryResponse == EMPTY_SUMMARY){
                            caller.onRequestError("Error occurred for ${code.code}")
                        } else {
                            caller.onSummaryRead(summaryResponse)
                        }
                    }
                }
            }
        }
    }

    private fun parseWebResponse(summaryCode: Code, json: JSONObject): Summary {
        try {
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
            return Summary(summaryCode, updatedAt, todayCount, totalCount)
        } catch (e: Exception) {
            return EMPTY_SUMMARY
        }

    }

}