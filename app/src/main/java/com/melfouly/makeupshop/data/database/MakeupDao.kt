package com.melfouly.makeupshop.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.melfouly.makeupshop.makeupcart.CartAdapter

@Dao
interface MakeupDao {

    /**
     * Makeup items table methods.
     */

    @Query("SELECT * FROM makeup_table")
    fun getAllItems(): MutableList<MakeupItemEntity>

    @Query("SELECT * FROM makeup_table WHERE id = :id")
    fun getItemById(id: Long): MakeupItemEntity

    @Query("SELECT * FROM makeup_table WHERE category = :category")
    fun getItemsByCategory(category: String): MutableList<MakeupItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(list: MutableList<MakeupItemEntity>)

    @Query("DELETE FROM makeup_table")
    suspend fun deleteAll()

    /**
     * Cart item table methods.
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToCart(item: CartEntity)

    @Query("SELECT * FROM cart_table ORDER BY name ASC")
    fun getAllCartItems(): List<CartEntity>

    @Delete
    suspend fun deleteFromCart(cartEntity: CartEntity)
}