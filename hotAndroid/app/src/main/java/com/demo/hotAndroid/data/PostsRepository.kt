package com.demo.hotAndroid.data

import androidx.lifecycle.MutableLiveData
import com.demo.hotAndroid.api.DataService
import com.demo.hotAndroid.data.model.Listing
import com.demo.hotAndroid.data.model.PostData
import javax.inject.Inject

class PostsRepository @Inject constructor(val dataService: DataService) {
    var posts: MutableLiveData<Resource<List<PostData>>> =
        MutableLiveData(Resource(Status.LOADING, null, null))
    var cache : MutableList<PostData> = mutableListOf()

    init {
        dataService.getPosts(after = null, success = {
            cache = it.data.children.map { post -> post.data }.toMutableList()
            posts.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            posts.postValue(Resource(Status.ERROR, null, it.message))
        })
    }

    private fun loadMore(after : String) {
        if (posts.value?.status == Status.LOADING) {
            return
        }
        dataService.getPosts(after = after, success = {
            val newPosts = it.data.children.map { post -> post.data }
            cache.addAll(cache.size - 1, newPosts)
            posts.postValue(Resource(Status.SUCCESS, cache, null))
        }, failure = {
            posts.postValue(Resource(Status.ERROR, null, it.message))
        })

    }


}