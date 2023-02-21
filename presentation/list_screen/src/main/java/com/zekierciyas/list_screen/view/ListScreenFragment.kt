package com.zekierciyas.list_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zekierciyas.cache.model.satellite_list.SatelliteListItem
import com.zekierciyas.list_screen.view_model.ListScreenUiState
import com.zekierciyas.list_screen.view_model.ListScreenViewModel
import com.zekierciyas.list_screen.adapter.ListScreenAdapter
import com.zekierciyas.list_screen.databinding.FragmentListScreenBinding
import com.zekierciyas.utility.extentions.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListScreenFragment: Fragment(), ListScreenAdapter.OnClickListener {

    private val viewModel: ListScreenViewModel by viewModels()
    private lateinit var binding: FragmentListScreenBinding
    @Inject
    lateinit var adapter: ListScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListScreenBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStoryRecyclerView()
        queryTextListener()
    }

    private fun setupStoryRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter.setClickListener(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onClick(item: SatelliteListItem) {
        Timber.d("${item.name} is clicked")
    }

    private fun queryTextListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideSoftKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                //Allows character changes to pass into the adapter class
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}