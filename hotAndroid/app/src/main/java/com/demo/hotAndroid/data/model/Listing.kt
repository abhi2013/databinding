package com.demo.hotAndroid.data.model

data class Listing(val data : ListingData) {
}

data class ListingData(val children : List<Post>) {
}