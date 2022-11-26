package com.melfouly.makeupshop.makeupdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.melfouly.makeupshop.data.LocalRepository
import com.melfouly.makeupshop.data.database.CartEntity
import com.melfouly.makeupshop.data.database.LocalDb
import com.melfouly.makeupshop.model.MakeupItem
import kotlinx.coroutines.launch

private const val TAG = "MakeupDetailsViewModel"

class MakeupDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = LocalRepository(LocalDb.createMakeupDao(application))

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