package com.demo.dataDemo.util

import android.util.Log
import javax.inject.Singleton

@Singleton
class Logger {
    companion object {
        private const val TAG = "HotAndroidDemo"
        fun verbose(message: String) {
            Log.v(TAG, message)
        }
    }

}