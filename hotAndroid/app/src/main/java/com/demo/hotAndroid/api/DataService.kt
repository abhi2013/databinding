package com.demo.hotAndroid.api

import com.android.volley.*
import com.android.volley.toolbox.*
import com.demo.hotAndroid.BuildConfig
import com.demo.hotAndroid.Configuration
import com.demo.hotAndroid.HotAndroidApp
import com.demo.hotAndroid.data.model.Listing
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataService @Inject constructor(val app: HotAndroidApp) {
    private val queue: RequestQueue
    private val serializer: Moshi

    init {
        VolleyLog.DEBUG = BuildConfig.DEBUG
        serializer = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        // Disable network caching to avoid seeing stale data
        val cache = NoCache()
        // Use default network stack since no proxy needed
        val networkStack = HurlStack()
        val network = BasicNetwork(networkStack)
        queue = RequestQueue(cache, network)
        queue.start()
    }

    fun getPosts(after: String?, success: (Listing) -> Unit, failure: (VolleyError) -> Unit) {
        var url = Configuration.kServerUrl + Configuration.kPostsEndpoint
        if (after != null) {
            url = "$url?after=$after"
        }
        val request = StringRequest(Request.Method.GET, url, Response.Listener { data ->
            val response = serializer.adapter<Listing>(Listing::class.java).fromJson(data)
            if (response != null) {
                success(response)
            } else {
                failure(VolleyError("Unexpected response data"))
            }
        }, Response.ErrorListener(failure))
        queue.add(request)
    }

}