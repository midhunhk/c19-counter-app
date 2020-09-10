package com.ae.apps.c19counter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ae.apps.c19counter.R
import com.ae.apps.c19counter.data.models.Summary
import kotlinx.android.synthetic.main.summary_item.view.*
import java.text.DecimalFormat

class SummaryListAdapter(var data: List<Summary>) :
    RecyclerView.Adapter<SummaryListAdapter.SummaryViewHolder>() {

    companion object {
        val NUMBER_FORMAT = DecimalFormat("#,###,###")
    }

    fun setItems(items: List<Summary>){
        data = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        val summaryItem = data[position]
        holder.placeCode.text = summaryItem.code
        holder.updatedAt.text = summaryItem.updatedAt
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