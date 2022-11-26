package com.melfouly.makeupshop.makeuplist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melfouly.makeupshop.data.LocalRepository
import com.melfouly.makeupshop.data.database.LocalDb
import com.melfouly.makeupshop.model.MakeupItem
import kotlinx.coroutines.launch

private const val TAG = "MakeupListViewModel"

class MakeupListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LocalRepository(LocalDb.createMakeupDao(application))

    private val _makeupList = MutableLiveData<List<MakeupItem>>()
    val makeupList: LiveData<List<MakeupItem>> get() = _makeupList

    private val _detailedItem = MutableLiveData<MakeupItem?>()
    val detailedItem: LiveData<MakeupItem?> get() = _detailedItem


    init {
        /**
         * Gets the makeupList from the repository getMakeupList method and save it as liveData then
         * calls the repository saveMakeupListIntoDb method
         */
        viewModelScope.launch {
            try {
                _makeupList.value = repository.getMakeupList()
                repository.saveMakeupListIntoDb()
            } catch (e: Exception) {
                Log.e(TAG, "Error in init: ${e.localizedMessage}")
            }
        }
    }


    // Refreshes the makeupList by shuffling the data.
    fun refreshList() {
        viewModelScope.launch {
            _makeupList.value = _makeupList.value?.toMutableList()?.shuffled()?.toList()
            Log.d(TAG, "RefreshList called in viewModel")
        }
    }

    // Save clicked makeup item from RecyclerView to be observed later.
    fun onMakeupItemClicked(makeupItem: MakeupItem) {
        _detailedItem.value = makeupItem
    }

    // Event for Navigating to Details Fragment Successfully and make the values null again.
    fun doneNavigating() {
        _detailedItem.value = null
    }

    // Filter receyclerView by chosen category.
    fun filterByCategory(category: String) {
        viewModelScope.launch {
            if (category == "All") {
                _makeupList.value = repository.getMakeupList()
            } else {
                _makeupList.value = repository.getMakeupListByCategory(category.lowercase())
            }
        }
    }


}