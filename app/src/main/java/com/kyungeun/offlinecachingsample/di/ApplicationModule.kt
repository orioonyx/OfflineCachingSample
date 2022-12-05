package com.kyungeun.offlinecachingsample.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.kyungeun.offlinecachingsample.data.api.ProductApi
import com.kyungeun.offlinecachingsample.data.database.ProductDatabase
import com.kyungeun.offlinecachingsample.data.repository.ProductRepository
import com.kyungeun.offlinecachingsample.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideProductApi(): ProductApi =
        Retrofit.Builder()
            .baseUrl(ProductApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ProductApi::class.java)

    @Provides
    fun provideMainRepository(api: ProductApi, db: ProductDatabase): ProductRepository = ProductRepositoryImpl(api, db)

    @Singleton
    @Provides
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, ProductDatabase::class.java, "product_database")
            .addMigrations()
            .build()
}