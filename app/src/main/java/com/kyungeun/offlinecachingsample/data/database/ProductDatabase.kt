package com.kyungeun.offlinecachingsample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kyungeun.offlinecachingsample.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}