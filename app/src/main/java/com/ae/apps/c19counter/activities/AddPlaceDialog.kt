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

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.ae.apps.c19counter.R
import com.ae.apps.c19counter.data.models.CODE_INDIA
import com.ae.apps.c19counter.data.models.Code
import com.ae.apps.c19counter.data.models.SummaryType

// https://www.mysamplecode.com/2012/03/android-spinner-arrayadapter.html
// https://stackoverflow.com/questions/51916726/where-to-put-viewmodel-observers-in-a-dialogfragment
class AddPlaceDialog : DialogFragment() {

    private lateinit var callback: AddPlaceDialogCallback

    companion object {
        fun getInstance() = AddPlaceDialog()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as AddPlaceDialogCallback
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_place, null)
        initRadioGroups(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun initRadioGroups(view: View) {
        view.findViewById<RadioButton>(R.id.radioCountry).isChecked = true
        view.findViewById<Spinner>(R.id.spinnerState).visibility = View.GONE
    }

    private fun handleUi(view: View) {
        val radioCountry = view.findViewById<RadioButton>(R.id.radioCountry)
        val radioState = view.findViewById<RadioButton>(R.id.radioState)
        val spinnerState = view.findViewById<Spinner>(R.id.spinnerState)
        val spinnerCountry = view.findViewById<Spinner>(R.id.spinnerCountry)

        radioCountry.setOnClickListener {
            if (radioCountry.isChecked) {
                spinnerState.visibility = View.GONE
                spinnerCountry.visibility = View.VISIBLE
            }
        }
        radioState.setOnClickListener {
            if (radioState.isChecked) {
                spinnerState.visibility = View.VISIBLE
                spinnerCountry.visibility = View.GONE
            }
        }

        val addButton = view.findViewById<Button>(R.id.btnAddPlace2)
        addButton.setOnClickListener {
            var code: Code = CODE_INDIA
            if (radioState.isChecked) {
                val placeType = SummaryType.STATE
                val placeCode =
                    resources.getStringArray(R.array.india_states_codes_array)[spinnerState.selectedItemPosition]
                code = Code(placeCode, placeType)
            }
            if (radioCountry.isChecked) {
                val placeType = SummaryType.COUNTRY
                val placeCode =
                    resources.getStringArray(R.array.country_codes_array)[spinnerCountry.selectedItemPosition]
                code = Code(placeCode, placeType)
            }

            callback.addPlace(code)
            this.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*view.findViewById<Button>(R.id.btnAddPlace2).setOnClickListener {
            this.dismiss()
        }*/
        handleUi(view)
    }
}

interface AddPlaceDialogCallback {
    fun addPlace(code: Code)
}