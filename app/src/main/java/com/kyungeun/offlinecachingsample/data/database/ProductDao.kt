package com.kyungeun.offlinecachingsample.data.database

import androidx.room.*
import com.kyungeun.offlinecachingsample.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getProduct(id : Int): Flow<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Delete
    suspend fun deleteProduct(product: Product)
}