package com.kyungeun.offlinecachingsample.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kyungeun.offlinecachingsample.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: ProductRepository
) : ViewModel() {

    val products = repository.getProducts().asLiveData()
}