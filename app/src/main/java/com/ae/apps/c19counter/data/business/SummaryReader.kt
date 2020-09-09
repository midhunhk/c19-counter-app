package com.ae.apps.c19counter.data.business

import com.ae.apps.c19counter.data.models.Summary

/**
 * Interface for implementing callback for reading Summary
 */
interface SummaryReader {

    /**
     * Callback method when a summary is read
     */
    public fun onSummaryRead(summary:Summary)
}