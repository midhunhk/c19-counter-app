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
package com.ae.apps.c19counter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ae.apps.c19counter.R
import com.ae.apps.c19counter.data.models.Summary
import kotlinx.android.synthetic.main.summary_item.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class SummaryListAdapter(var data: List<Summary>) :
    RecyclerView.Adapter<SummaryListAdapter.SummaryViewHolder>() {

    companion object {
        val TIMEZONE: TimeZone = Calendar.getInstance().timeZone
        val NUMBER_FORMAT = DecimalFormat("#,###,###")
        @SuppressLint("ConstantLocale")
        val DATE_FORMAT_IN = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    }

    fun setItems(items: List<Summary>){
        data = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun formatUpdatedAt(updatedAt:String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        dateFormat.timeZone = TIMEZONE
        return dateFormat.format( DATE_FORMAT_IN.parse(updatedAt)!! )
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val summaryItem = data[position]
        holder.placeCode.text = summaryItem.code
        holder.updatedAt.text = formatUpdatedAt(summaryItem.updatedAt)
        holder.confirmedToday.text = NUMBER_FORMAT.format(summaryItem.todayCount.confirmed)
        holder.confirmedTotal.text = NUMBER_FORMAT.format(summaryItem.totalCount.confirmed)
        holder.recoveredToday.text = NUMBER_FORMAT.format(summaryItem.todayCount.recovered)
        holder.recoveredTotal.text = NUMBER_FORMAT.format(summaryItem.totalCount.recovered)
        holder.deceasedToday.text = NUMBER_FORMAT.format(summaryItem.todayCount.deceased)
        holder.deceasedTotal.text = NUMBER_FORMAT.format(summaryItem.totalCount.deceased)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        return SummaryViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.summary_item, parent, false)
        )
    }

    class SummaryViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val placeCode = view.textCountryOrState!!
        val updatedAt = view.textLastUpdated!!
        val confirmedToday = view.textConfirmedToday!!
        val confirmedTotal = view.textConfirmedTotal!!
        val recoveredToday = view.textRecoveredToday!!
        val recoveredTotal = view.textRecoveredTotal!!
        val deceasedToday = view.textDeceasedToday!!
        val deceasedTotal = view.textDeceasedTotal!!
    }
}