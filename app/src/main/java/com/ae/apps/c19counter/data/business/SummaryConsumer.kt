package com.ae.apps.c19counter.data.business

import com.ae.apps.c19counter.data.models.Code
import com.ae.apps.c19counter.data.models.Summary

/**
 * Interface for implementing callback for reading Summary
 */
interface SummaryConsumer {

    /**
     * Callback method when a summary is read
     */
    fun onSummaryRead(summary: Summary)

    /**
     *
     */
    fun addPlace(code: Code)

    /**
     *
     */
    fun removePlace(code: Code)
}