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
package com.ae.apps.c19counter.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ae.apps.c19counter.R
import com.ae.apps.c19counter.adapters.SummaryListAdapter
import com.ae.apps.c19counter.data.business.SummaryCounter
import com.ae.apps.c19counter.data.business.SummaryReader
import com.ae.apps.c19counter.data.models.CODE_INDIA
import com.ae.apps.c19counter.data.models.Code
import com.ae.apps.c19counter.data.models.Summary
import com.ae.apps.c19counter.data.viemodel.CodeViewModel
import com.ae.apps.lib.common.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SummaryReader {

    private val summaryCounter: SummaryCounter by lazy { SummaryCounter.getInstance() }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SummaryListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val codeViewModel: CodeViewModel by viewModels()

    private var placeCodesCache: List<Code>? = null
    private val responseList = mutableListOf<Summary>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        setUpPullToRefresh()
        setUpMenu()
        initViewModel()
        initUI()
    }

    private fun initUI(){
        addIcon.setOnClickListener {
            val dialog = AddPlaceDialog.getInstance()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.show(dialog)
        }
    }

    private fun initViewModel() {
        codeViewModel.delete(CODE_INDIA)
        codeViewModel.getPlaceCodes().observe(this, {
            placeCodes ->
                run {
                    refreshData(placeCodes)
                    placeCodesCache = placeCodes
                }
        })
    }

    private fun setUpMenu() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_about -> {
                    DialogUtils.showCustomViewDialog(
                        this@MainActivity,
                        layoutInflater,
                        R.layout.dialog_add_place,
                        R.string.str_about
                    )
                }
                R.id.menu_privacy -> {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(getString(R.string.url_privacy_policy))
                    startActivity(intent)
                }
            }
            true
        }
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

            // Set the empty view
            setEmptyView(noResultsView)
        }
    }

    private fun setUpPullToRefresh() {
        pullToRefresh.setOnRefreshListener {
            if(null != placeCodesCache) {
                refreshData(placeCodesCache!!)
                pullToRefresh.isRefreshing = false
            }
        }
    }

    private fun refreshData(placeCodes:List<Code>) {
        if(placeCodes.isNotEmpty()) {
            progressbar.visibility = View.VISIBLE
            textUpdatedAt.text = getString(R.string.str_updating)
            summaryCounter.retrieveSummary(placeCodes, this)
        }
    }

    override fun onSummaryRead(summary: Summary) {
        updateResponseList(summary)
        progressbar.visibility = View.INVISIBLE
    }

    private fun updateResponseList(summary: Summary) {
        textUpdatedAt.text = getString(R.string.str_updated)
        val existingItems = responseList.filter { it.summaryCode.code == summary.summaryCode.code }
        if (existingItems.isEmpty()) {
            responseList.add(summary)
        } else {
            val index = responseList.indexOf(existingItems[0])
            responseList[index] = summary
        }
        viewAdapter.setItems(responseList)
        recyclerView.adapter = viewAdapter
        recyclerView.scheduleLayoutAnimation()
    }

}