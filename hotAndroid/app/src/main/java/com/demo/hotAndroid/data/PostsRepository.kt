package com.demo.hotAndroid.data

import androidx.lifecycle.MutableLiveData
import com.demo.hotAndroid.api.DataService
import com.demo.hotAndroid.data.model.Listing
import com.demo.hotAndroid.data.model.PostData
import javax.inject.Inject

class PostsRepository @Inject constructor(val dataService: DataService) {
    var posts: MutableLiveData<Resource<List<PostData>>> =
        MutableLiveData(Resource(Status.LOADING, null, null))
    var cache: MutableList<PostData> = mutableListOf()

    init {
        dataService.getPosts(after = null, success = {
            cache = it.data.children.map { post -> post.data }.toMutableList()
            posts.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            posts.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

    fun reset() {
        cache = mutableListOf()
        posts.value = null
    }

    fun loadMore() {
        if (posts.value?.status == Status.LOADING) {
            return
        }
        var after: String? = null
        if (!cache.isNullOrEmpty()) {
            after = cache.last().id
        }
        dataService.getPosts(after = after, success = {
            val newPosts = it.data.children.map { post -> post.data }
            cache.addAll(cache.size - 1, newPosts)
            posts.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            posts.postValue(Resource(Status.ERROR, null, "Could not load more posts."))
        })
    }

}