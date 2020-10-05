package com.demo.hotAndroid.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.hotAndroid.data.PostsRepository
import com.demo.hotAndroid.data.Resource
import com.demo.hotAndroid.data.Status
import com.demo.hotAndroid.data.model.PostData
import com.demo.hotAndroid.data.model.Posts
import javax.inject.Inject

class PostsViewModel @Inject constructor(val postsRepository: PostsRepository) : ViewModel() {
    class PostEntry(val id: String, var title: String, var author: String) {
        init {
            title = "Title: $title"
            author = "Author: $author"
        }
    }

    private lateinit var posts : MutableLiveData<Resource<List<PostData>>>
    lateinit var postEntries : MediatorLiveData<Resource<List<PostEntry>>>

    fun fetch() {
        posts = postsRepository.posts
        postEntries = MediatorLiveData<Resource<List<PostEntry>>>().apply {
            addSource(posts) { update() }
        }
    }

    fun update() {
        if (posts.value!!.status == Status.LOADING) {
            postEntries.value = Resource.loading(null)
            return
        }

        val posts = posts.value?.data ?: emptyList()
        val newPosts = posts.map { post -> PostEntry(id= post.id,title = post.title, author = post.author) }
        postEntries.postValue(Resource.success(newPosts))
    }

    fun loadMore() {
        postsRepository.loadMore()
    }
}