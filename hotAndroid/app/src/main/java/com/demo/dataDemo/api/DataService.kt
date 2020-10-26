package com.demo.dataDemo.api

import com.demo.dataDemo.Configuration
import com.demo.dataDemo.DemoAndroidApp
import com.demo.dataDemo.data.model.InputNumbers
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataService @Inject constructor(val app: DemoAndroidApp, val config: Configuration) {
    private val serializer: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    var typeIntNumbers =
        Types.newParameterizedType(InputNumbers::class.java, Int::class.javaObjectType)
    private val adapter = serializer.adapter<InputNumbers<Int>>(typeIntNumbers)

    private fun readFile(filename: String): String? {
        return try {
            val inputStream = File(app.applicationContext.filesDir, filename).inputStream()
             inputStream.bufferedReader().use { it.readText() }
        }catch (e : Exception) {
            null
        }
    }

    fun getNumbers(success: (InputNumbers<Int>) -> Unit, failure: (Error) -> Unit) {
        val json = readFile(config.cacheFileName)
        if (json == null) {
            failure(Error("Input numbers file could not be read"))
            return
        }

        val response =
            adapter.fromJson(json)
        if (response != null) {
            success(response)
        } else {
            failure(Error("Input numbers could not be parsed"))
        }
    }

    fun updateNumbers(newNumbers: InputNumbers<Int>, success: (InputNumbers<Int>) -> Unit, failure: (Error) -> Unit) {
        if (newNumbers != null) {
            save(newNumbers)
            success(newNumbers)
        } else {
            failure(Error("Input numbers could not be parsed"))
        }
    }

    private fun save(numbers : InputNumbers<Int>) {
        val json = adapter.toJson(numbers)
        val writer =  File(app.applicationContext.filesDir, config.cacheFileName).bufferedWriter()
        writer.use { out -> out.write(json) }

    }

}