package com.ae.apps.c19counter.data.models

data class Count(val confirmed:Int, val recovered:Int, val deceased:Int, val tested:Int = 0)

val EMPTY_COUNT = Count(0, 0, 0)