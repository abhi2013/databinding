package com.demo.hotAndroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.demo.hotAndroid.api.DataService
import com.demo.hotAndroid.data.PostsRepository
import com.demo.hotAndroid.data.Resource
import com.demo.hotAndroid.data.model.Posts
import javax.inject.Inject

class PostsViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var postsRepository: PostsRepository
    open fun fetch() : LiveData<Resource<Posts>> {
        return postsRepository.posts
    }

    fun loadMore(){

    }
}