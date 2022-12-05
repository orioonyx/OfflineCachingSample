package com.kyungeun.offlinecachingsample.data.api

import com.kyungeun.offlinecachingsample.data.model.Product
import retrofit2.http.GET

interface ProductApi {
    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }

    @GET("products")
    suspend fun getProducts(): List<Product>
}