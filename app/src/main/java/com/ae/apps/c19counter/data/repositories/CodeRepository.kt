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

package com.ae.apps.c19counter.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.ae.apps.c19counter.data.AppDatabase
import com.ae.apps.c19counter.data.dao.CodeDao
import com.ae.apps.c19counter.data.models.Code
import org.jetbrains.anko.doAsync

class CodeRepository (application: Application) {

    private var codeDao:CodeDao

    private var allCodes :LiveData< List<Code>>

    init {
        val database = AppDatabase.getInstance(
            application.applicationContext
        )!!
        codeDao = database.codeDao()
        allCodes = codeDao.getAll()
    }

    fun insertCode(code: Code) {
        doAsync {
            codeDao.insert(code)
        }
    }

    fun deleteCode(code: Code) {
        doAsync {
            codeDao.delete(code)
        }
    }

    fun getAllCodes() = codeDao.getAll()

    fun findCodeByValue(code:String) = codeDao.findByCode(code)

}