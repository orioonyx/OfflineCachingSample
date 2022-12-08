package com.kyungeun.offlinecachingsample.data.repository

import androidx.room.withTransaction
import com.kyungeun.offlinecachingsample.data.api.ProductApi
import com.kyungeun.offlinecachingsample.data.database.ProductDatabase
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.util.Resource
import com.kyungeun.offlinecachingsample.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val api: ProductApi,
    private val db: ProductDatabase
) : ProductRepository {

    private val dao = db.productDao()

    override fun getProducts(): Flow<Resource<List<Product>>> = networkBoundResource(
        query = {
            dao.getAllProducts()
        },
        fetch = {
            delay(1000)
            api.getProducts()
        },
        saveFetchResult = { products ->
            db.withTransaction {
                dao.deleteAllProducts()
                dao.insertAllProducts(products)
            }
        }
    )

    override fun getProduct(id: Int): Flow<Resource<Product>> = networkBoundResource(
        query = {
            dao.getProduct(id)
        },
        fetch = {
            delay(500)
            api.getProduct(id)
        },
        saveFetchResult = { product ->
            db.withTransaction {
                dao.deleteProduct(product)
                dao.insertProduct(product)
            }
        }
    )

    override suspend fun deleteAllProducts(): Resource<Unit> {
        return try {
            db.withTransaction {
                dao.deleteAllProducts()
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}