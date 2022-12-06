package com.melfouly.makeupshop.makeupdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.melfouly.makeupshop.data.LocalRepository
import com.melfouly.makeupshop.data.database.CartEntity
import com.melfouly.makeupshop.data.database.LocalDb
import com.melfouly.makeupshop.model.MakeupItem
import kotlinx.coroutines.launch

private const val TAG = "MakeupDetailsViewModel"

class MakeupDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LocalRepository(LocalDb.createMakeupDao(application))

    private val _detailedItem = MutableLiveData<MakeupItem?>()
    val detailedItem: LiveData<MakeupItem?> get() = _detailedItem

    // Get item by id from makeupItems db.
    fun getItemById(id: Long) {
        viewModelScope.launch {
            _detailedItem.value = repository.getMakeupItemById(id)
        }
    }

    // Save item into cart's db.
    fun saveItemToCart(cartEntity: CartEntity) {
        viewModelScope.launch {
            repository.saveItemToCart(cartEntity)
        }
    }

    // Delete item from cart's db.
    fun deleteItemFromCart(makeupItem: MakeupItem) {
        viewModelScope.launch {
            repository.deleteItemFromCart(makeupItem)
        }
    }

}