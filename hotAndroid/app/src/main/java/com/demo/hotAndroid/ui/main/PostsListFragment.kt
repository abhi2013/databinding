package com.demo.hotAndroid.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.hotAndroid.R
import com.demo.hotAndroid.viewmodel.PostsViewModel

class PostsListFragment : Fragment() {

    companion object {
        fun newInstance() = PostsListFragment()
    }

    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

}