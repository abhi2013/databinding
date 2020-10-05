package com.demo.hotAndroid.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.demo.hotAndroid.R
import com.demo.hotAndroid.databinding.PostsListFragmentBinding
import com.demo.hotAndroid.viewmodel.PostsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostsViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<PostsListFragmentBinding>(layoutInflater,
        R.layout.posts_list_fragment, container, false)
        viewModel.fetch()
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


}