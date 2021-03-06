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

package com.ae.apps.c19counter.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

// Enums
enum class SummaryType {
    COUNTRY, STATE, UNKNOWN
}

// Data Classes
@Entity
data class Code(
    @PrimaryKey
    @ColumnInfo(name = "place_code") val code: String,
    @ColumnInfo(name = "summary_type") val type: SummaryType,
    @ColumnInfo(name = "name") val name: String? = ""
)

data class Summary(
    val summaryCode: Code,
    val updatedAt: String = "",
    val todayCount: Count = EMPTY_COUNT,
    val totalCount: Count = EMPTY_COUNT
)

data class Count(val confirmed: Int, val recovered: Int, val deceased: Int, val tested: Int = 0)

// Null Objects
val EMPTY_COUNT = Count(0, 0, 0)
val CODE_INDIA = Code("IN", SummaryType.COUNTRY)

class Converters {
    @TypeConverter
    fun fromSummaryString(summaryType:String) : SummaryType{
        return SummaryType.valueOf(summaryType)
    }

    @TypeConverter
    fun toSummaryString(summaryType: SummaryType) : String {
        return summaryType.toString()
    }
}

val EMPTY_CODE = Code("UNK", SummaryType.UNKNOWN)
val EMPTY_SUMMARY = Summary(EMPTY_CODE)