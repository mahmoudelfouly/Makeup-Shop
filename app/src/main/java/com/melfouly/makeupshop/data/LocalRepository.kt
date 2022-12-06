package com.melfouly.makeupshop.data

import android.util.Log
import com.melfouly.makeupshop.data.database.*
import com.melfouly.makeupshop.data.network.Network
import com.melfouly.makeupshop.model.MakeupItem
import com.melfouly.makeupshop.model.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

private const val TAG = "LocalRepository"

class LocalRepository(
    private val makeupDao: MakeupDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    // Transfer items from db to be liveData from model items.
    suspend fun getMakeupList(): MutableList<MakeupItem> = withContext(ioDispatcher) {
        return@withContext makeupDao.getAllItems().shuffled().toMutableList().asDomainModel()
    }

    // Get makeupItem by Id.
    suspend fun getMakeupItemById(id: Long): MakeupItem = withContext(ioDispatcher) {
        return@withContext makeupDao.getItemById(id).asDomainModel()
    }

    // Filter makeupList by Category.
    suspend fun getMakeupListByCategory(category: String): MutableList<MakeupItem> =
        withContext(ioDispatcher) {
            return@withContext makeupDao.getItemsByCategory(category).shuffled().toMutableList()
                .asDomainModel()
        }

    // Take response from the network and save the items in db.
    suspend fun saveMakeupListIntoDb() = withContext(ioDispatcher) {
        try {
            val makeupList: List<MakeupItem>

            val response = Network.makeupCall.getAllProducts().awaitResponse()
            // Check the response, if successful insert it to db, if not Log error will appear.
            if (response.isSuccessful) {
                makeupList = response.body()!!
                makeupDao.insertItems(makeupList.asDatabaseModel())
                Log.d(TAG, "Network call done, List inserted to db successfully")
            } else {
                Log.e(TAG, "Response is not successful")
            }

        } catch (ex: Exception) {
            Log.e(TAG, "Error in saveMakeupList: ${ex.localizedMessage}")
        }

    }

    // Save certain item to cart's db.
    suspend fun saveItemToCart(cartEntity: CartEntity) = withContext(ioDispatcher) {
        try {
            makeupDao.insertToCart(cartEntity)
        } catch (ex: Exception) {
            Log.e(TAG, "Error in saveItemToCart: ${ex.localizedMessage}")
        }
    }

    // Get all cart items from db.
    suspend fun getAllCartItems(): List<MakeupItem> = withContext(ioDispatcher) {
        makeupDao.getAllCartItems().asDomainCartModel()
    }

    // Delete certain item from cart.
    suspend fun deleteItemFromCart(makeupItem: MakeupItem) = withContext(ioDispatcher) {
        try {
            val deletedItem = makeupItem.asDatabaseCartModel()
            makeupDao.deleteFromCart(deletedItem)
        } catch (ex: Exception) {
            Log.e(TAG, "Error in deleteItemFromCart: ${ex.localizedMessage}")

        }
    }

}