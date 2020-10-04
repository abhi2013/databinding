package com.demo.hotAndroid.util

import android.util.Log

class Logger {
    companion object {
        private const val TAG = "HotAndroidDemo"
        fun verbose(message: String) {
            Log.v(TAG, message)
        }
    }

}