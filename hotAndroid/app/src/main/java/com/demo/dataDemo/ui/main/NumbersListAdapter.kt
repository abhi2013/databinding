package com.demo.dataDemo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.dataDemo.R
import com.demo.dataDemo.databinding.NumbersListItemBinding
import com.demo.dataDemo.viewmodel.NumbersViewModel

class NumbersListAdapter<T>(private val clickListener: (NumbersViewModel.Entry<T>) -> Unit) :
    ListAdapter<NumbersViewModel.Entry<T>, NumbersListAdapter.ItemViewHolder<T>>(DiffCallback<T>()) {

    class ItemViewHolder<T>(
        private val binding: ViewDataBinding,
        private val entryClickListener: (NumbersViewModel.Entry<T>) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NumbersViewModel.Entry<T>) = with(itemView) {
            with(binding as? NumbersListItemBinding) {
                this?.entry = item
                this?.clicklistener = View.OnClickListener {
                    entryClickListener(item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: NumbersListAdapter.ItemViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.numbers_list_item,
            parent,
            false
        )
        return ItemViewHolder(
            binding,
            clickListener
        )
    }

    class DiffCallback<T> : DiffUtil.ItemCallback<NumbersViewModel.Entry<T>>() {
        override fun areItemsTheSame(
            oldItem: NumbersViewModel.Entry<T>,
            newItem: NumbersViewModel.Entry<T>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NumbersViewModel.Entry<T>,
            newItem: NumbersViewModel.Entry<T>
        ): Boolean {
            return oldItem.number == newItem.number
        }
    }

}