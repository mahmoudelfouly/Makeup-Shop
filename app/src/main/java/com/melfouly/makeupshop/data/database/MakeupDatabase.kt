package com.melfouly.makeupshop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

// Room Database that contains the makeup items table and cart table.
@Database(entities = [MakeupItemEntity::class, CartEntity::class], version = 6, exportSchema = false)
abstract class MakeupDatabase: RoomDatabase() {
    abstract fun makeupDao(): MakeupDao
}