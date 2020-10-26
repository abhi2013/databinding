package com.demo.dataDemo.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.dataDemo.R
import com.demo.dataDemo.data.Status
import com.demo.dataDemo.databinding.NumbersListFragmentBinding
import com.demo.dataDemo.util.Logger
import com.demo.dataDemo.viewmodel.NumbersViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class NumbersFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: NumbersViewModel by activityViewModels {
        viewModelFactory
    }
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: NumbersListAdapter<Int>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<NumbersListFragmentBinding>(
            layoutInflater,
            R.layout.numbers_list_fragment, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewManager = LinearLayoutManager(context)
        viewAdapter = NumbersListAdapter { number ->
            Logger.verbose("Tapped number : ${number.number.value}")
        }

        val numbersList = binding.postsList
        numbersList.layoutManager = viewManager
        numbersList.adapter = viewAdapter
        viewModel.fetch()
        viewModel.entries.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                viewAdapter.submitList(it.data)
                viewAdapter.notifyDataSetChanged()
            }
        })

        // Added a listener for the 'Done' key on the soft-keyboard being pressed
        binding.number.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
               viewModel.addNumber()
            }
            false
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

}