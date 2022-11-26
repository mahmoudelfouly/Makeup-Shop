package com.melfouly.makeupshop.data.database

import android.content.Context
import androidx.room.Room

// Singleton class that is used to create makeup database.
object LocalDb {

    // Static method that creates a class to return the DAO of the makeup.
    fun createMakeupDao(context: Context): MakeupDao {
        return Room.databaseBuilder(
            context.applicationContext,
            MakeupDatabase::class.java,
            "makeup_database"
        ).fallbackToDestructiveMigration()
            .build().makeupDao()
    }

}