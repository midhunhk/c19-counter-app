package com.ae.apps.c19counter.data.models

enum class SummaryType {
    COUNTRY, STATE
}

data class Summary(
    val code: String,
    val type: SummaryType,
    val updatedAt: String = "",
    val todayCount: Count = EMPTY_COUNT,
    val totalCount: Count = EMPTY_COUNT,
    val name: String = ""
)