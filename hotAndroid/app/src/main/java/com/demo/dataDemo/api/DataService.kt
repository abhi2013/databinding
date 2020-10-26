package com.demo.dataDemo.api

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.demo.dataDemo.Configuration
import com.demo.dataDemo.DemoAndroidApp
import com.demo.dataDemo.data.model.InputNumbers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.InputStream
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataService @Inject constructor(val app: DemoAndroidApp, val config: Configuration) {
    private val serializer: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val adapter = serializer.adapter<InputNumbers<Int>>(InputNumbers::class.java)

    private fun readFile(filename: String): String? {
        val inputStream = File(filename).inputStream()
        return inputStream.bufferedReader().use { it.readText() }
    }

    fun getNumbers(success: (InputNumbers<Int>) -> Unit, failure: (Error) -> Unit) {
        val json = readFile(config.cacheFileName)
        if (json == null) {
            failure(Error("Input numbers file could not be read"))
        }

        val response =
            adapter.fromJson(json)
        if (response != null) {
            success(response)
        } else {
            failure(Error("Input numbers could not be parsed"))
        }
    }

    fun save(numbers : InputNumbers<Int>) {
        val json = adapter.toJson(numbers)
        val writer = File(config.cacheFileName).bufferedWriter()
        writer.use { out -> out.write(json) }

    }

}