package com.francis.housedesigntablet.utils

import android.util.Log

object UiUtils {

    internal fun appErrorLog(TAG: String?, message: String?) {
        Log.e("$TAG", "$message")
    }

}