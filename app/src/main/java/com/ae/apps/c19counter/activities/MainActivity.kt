package com.ae.apps.c19counter.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ae.apps.c19counter.R
import com.ae.apps.c19counter.adapters.SummaryListAdapter
import com.ae.apps.c19counter.data.business.SummaryCounter
import com.ae.apps.c19counter.data.business.SummaryReader
import com.ae.apps.c19counter.data.models.Summary
import com.ae.apps.c19counter.data.models.SummaryType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SummaryReader {

    private val summaryCounter: SummaryCounter by lazy { SummaryCounter.getInstance() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SummaryListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    // Create the initial list of items that need to be fetched
    private val requestList = mutableListOf<Summary>(
        Summary("IN", SummaryType.COUNTRY),
        Summary("KL", SummaryType.STATE)
    )

    private val responseList = mutableListOf<Summary>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()

        setUpPullToRefresh()

        refreshData()
    }

    private fun setUpRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = SummaryListAdapter(emptyList())

        recyclerView = list.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    private fun setUpPullToRefresh() {
        pullToRefresh.setOnRefreshListener {
            // Toast.makeText(baseContext, "Not yet implemented", Toast.LENGTH_SHORT).show()
            refreshData()
            pullToRefresh.isRefreshing = false
        }
    }

    private fun refreshData() {
        progressbar.visibility = View.VISIBLE
        summaryCounter.retrieveSummary(requestList, this)
    }

    override fun onSummaryRead(summary: Summary) {
        updateResponseList(summary)
        progressbar.visibility = View.INVISIBLE
    }

    private fun updateResponseList(summary: Summary) {
        textUpdatedAt.text = resources.getString(R.string.str_last_updated, summary.updatedAt)
        val existingItems = responseList.filter { it.code == summary.code }
        if (existingItems.isEmpty()) {
            responseList.add(summary)
        } else {
            val index = responseList.indexOf(existingItems[0])
            responseList[index] = summary
        }
        viewAdapter.setItems(responseList)
    }

}