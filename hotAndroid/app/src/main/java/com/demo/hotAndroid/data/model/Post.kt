package com.demo.hotAndroid.data.model

import com.squareup.moshi.Json

data class Post(val data : PostData) {
}

data class PostData(
    val id: String,
    @Json(name = "author_fullname") val author: String,
    val permalink: String,
    val title: String,
    val thumbnail: String? = null
) {

}
typealias Posts = List<PostData>