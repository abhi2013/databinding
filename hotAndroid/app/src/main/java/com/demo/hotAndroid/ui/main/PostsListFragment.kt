package com.demo.hotAndroid.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.hotAndroid.R
import com.demo.hotAndroid.data.Status
import com.demo.hotAndroid.databinding.PostsListFragmentBinding
import com.demo.hotAndroid.util.Logger
import com.demo.hotAndroid.viewmodel.PostsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostsViewModel by activityViewModels {
        viewModelFactory
    }
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: PostsListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<PostsListFragmentBinding>(
            layoutInflater,
            R.layout.posts_list_fragment, container, false
        )
        viewManager = LinearLayoutManager(context)
        viewAdapter = PostsListAdapter( { post ->
            // TODO - fire an intent to open the post entry permalink
            Logger.verbose("Opening Post : ${post.title}")
        }
        ) {
            // load more data when the user has scrolled to end of the list
            viewModel.loadMore()
        }
        val postsList = binding.postsList
        postsList.layoutManager = viewManager
        postsList.adapter = viewAdapter
        viewModel.fetch()
        viewModel.postEntries.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing =  it.status == Status.LOADING
            if (it.status == Status.SUCCESS) {
                viewAdapter.submitList(it.data)
                viewAdapter.notifyDataSetChanged()
            }
        })
        // TODO - handle swipe down to refresh gesture
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


}