package com.unitalk.utils

import java.text.SimpleDateFormat
import java.util.*

class CurrentTimeManager {
    companion object {
        private val DATE_TIME_FORMAT = "yyyyMMdd HH:mm:ss"
        private val LOCALE = Locale.US
    }

    private val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT, LOCALE)

    fun getCurrentTime(currentDate: Date): String {
        return simpleDateFormat.format(currentDate)
    }
}