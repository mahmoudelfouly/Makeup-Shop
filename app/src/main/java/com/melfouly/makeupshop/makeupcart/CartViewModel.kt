package com.melfouly.makeupshop.makeupcart

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

class CartViewModel(application: Application): AndroidViewModel(application) {
    private val repository = LocalRepository(LocalDb.createMakeupDao(application))

    private val _cartList = MutableLiveData<List<MakeupItem>>()
    val cartList: LiveData<List<MakeupItem>> get() = _cartList

    private val _detailedItem = MutableLiveData<MakeupItem?>()
    val detailedItem: LiveData<MakeupItem?> get() = _detailedItem

    init {
        viewModelScope.launch {
            _cartList.value = repository.getAllCartItems()
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

    // Delete item from cart's db.
    fun deleteItemFromCart(makeupItem: MakeupItem) {
        viewModelScope.launch {
            repository.deleteItemFromCart(makeupItem)
        }
    }
}