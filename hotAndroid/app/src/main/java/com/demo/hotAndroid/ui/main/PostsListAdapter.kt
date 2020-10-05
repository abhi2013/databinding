package com.demo.hotAndroid.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.hotAndroid.R
import com.demo.hotAndroid.databinding.PostsListItemBinding
import com.demo.hotAndroid.viewmodel.PostsViewModel

class PostsListAdapter(val postClickListener: (PostsViewModel.PostEntry) -> Unit, private val endOfListListener : () -> Unit) :
    ListAdapter<PostsViewModel.PostEntry, PostsListAdapter.ItemViewHolder>(DiffCallback()) {

    class ItemViewHolder(
        val binding: ViewDataBinding,
        private val postClickListener: (PostsViewModel.PostEntry) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostsViewModel.PostEntry) = with(itemView) {
            with(binding as? PostsListItemBinding) {
                this?.entry = item
                this?.clicklistener = View.OnClickListener {
                    postClickListener(item)
                }
            }

        }
    }


    override fun onBindViewHolder(holder: PostsListAdapter.ItemViewHolder, position: Int) {
        if (position == itemCount -1 ) {
            endOfListListener()
        }
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.posts_list_item,
            parent,
            false
        )
        return ItemViewHolder(
            binding,
            postClickListener
        )
    }

    class DiffCallback : DiffUtil.ItemCallback<PostsViewModel.PostEntry>() {
        override fun areItemsTheSame(
            oldItem: PostsViewModel.PostEntry,
            newItem: PostsViewModel.PostEntry
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PostsViewModel.PostEntry,
            newItem: PostsViewModel.PostEntry
        ): Boolean {
            return oldItem.title == newItem.title && oldItem.author == newItem.author
        }
    }


}