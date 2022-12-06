package com.melfouly.makeupshop.makeuplist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.melfouly.makeupshop.R
import com.melfouly.makeupshop.databinding.FragmentMakeupListBinding
import com.melfouly.makeupshop.model.FilterLists.categoryList

private const val TAG = "MakeupListFragment"

class MakeupListFragment : Fragment() {

    private val viewModel: MakeupListViewModel by viewModels()
    private lateinit var binding: FragmentMakeupListBinding
    private lateinit var adapter: MakeupAdapter
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private var selectedCategoryPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentMakeupListBinding.inflate(inflater)

        // Get selectedCategoryPosition from savedInstanceState.
        if (savedInstanceState != null) {
            selectedCategoryPosition = savedInstanceState.getInt("selectedCategoryPosition")
        }

        // Setting category adapter ready.
        categoryAdapter =
            ArrayAdapter(requireActivity(), R.layout.category_menu_item, categoryList)

        // Setting category autoCompleteTextView.
        binding.autoComplete.run {
            setAdapter(categoryAdapter)
            setText(categoryAdapter.getItem(selectedCategoryPosition), false)
            isSaveEnabled = false
            freezesText = false
        }


        binding.autoComplete.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    viewModel.filterByCategory(categoryList[0])
                    selectedCategoryPosition = 0
                }
                1 -> {
                    viewModel.filterByCategory(categoryList[1])
                    selectedCategoryPosition = 1
                }
                2 -> {
                    viewModel.filterByCategory(categoryList[2])
                    selectedCategoryPosition = 2
                }
                3 -> {
                    viewModel.filterByCategory(categoryList[3])
                    selectedCategoryPosition = 3
                }
                4 -> {
                    viewModel.filterByCategory(categoryList[4])
                    selectedCategoryPosition = 4
                }
                5 -> {
                    viewModel.filterByCategory(categoryList[5])
                    selectedCategoryPosition = 5
                }
                6 -> {
                    viewModel.filterByCategory(categoryList[6])
                    selectedCategoryPosition = 6
                }
                7 -> {
                    viewModel.filterByCategory(categoryList[7])
                    selectedCategoryPosition = 7
                }
                8 -> {
                    viewModel.filterByCategory(categoryList[8])
                    selectedCategoryPosition = 8
                }
                9 -> {
                    viewModel.filterByCategory(categoryList[9])
                    selectedCategoryPosition = 9
                }
                10 -> {
                    viewModel.filterByCategory(categoryList[10])
                    selectedCategoryPosition = 10
                }
                11 -> {
                    viewModel.filterByCategory(categoryList[11])
                    selectedCategoryPosition = 11
                }
            }
        }

        // When swipe-to-refresh gesture call refreshList method from viewModel then make a toast
        // that the page refreshed successfully.
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshList()
            Log.d(TAG, "RefreshList called in fragment")
            binding.refreshLayout.isRefreshing = false
            Toast.makeText(requireActivity(), "Page refreshed successfully", Toast.LENGTH_SHORT)
                .show()
        }

        // Prepare the adapter from MakeupAdapter, take the clicked makeup item and store it
        // in viewModel
        adapter = MakeupAdapter(MakeupAdapter.MakeupItemClickListener { makeupItem ->
            viewModel.onMakeupItemClicked(makeupItem)
            Log.d(TAG, "Item id saved is: ${makeupItem.id}")
        })
        binding.recyclerview.adapter = adapter

        // Adjust adapter observer to position the screen to first item in the
        // recyclerView after refreshing.
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                binding.recyclerview.scrollToPosition(0)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.recyclerview.scrollToPosition(0)
            }
        })

        // Observe makeupList value and submitList into adapter.
        viewModel.makeupList.observe(viewLifecycleOwner) { makeupList ->
            makeupList?.let { adapter.submitList(it) }
        }

        // Observe detailedItem value, if clicked navigate to details fragment and make
        // detailedItem value equals null.
        viewModel.detailedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                this.findNavController().navigate(
                    MakeupListFragmentDirections.actionMakeupListFragmentToMakeupDetailsFragment(
                        it.id
                    )
                )
                Log.d(TAG, "Observing ${it.id}")
                viewModel.doneNavigating()
            }
        }

        // Once cartFab clicked navigate to cartFragment.
        binding.cartFab.setOnClickListener {
            this.findNavController()
                .navigate(MakeupListFragmentDirections.actionMakeupListFragmentToCartFragment())
        }

        return binding.root
    }

    // Save the selectedCategoryPosition in a bundle.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedCategoryPosition", selectedCategoryPosition)
    }


}