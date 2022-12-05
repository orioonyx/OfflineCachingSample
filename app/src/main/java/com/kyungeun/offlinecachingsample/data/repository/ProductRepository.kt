package com.kyungeun.offlinecachingsample.data.repository

import androidx.lifecycle.LiveData
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<Resource<List<Product>>>

    fun getProduct(id: Int): LiveData<Product>
}