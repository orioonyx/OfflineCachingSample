package com.kyungeun.offlinecachingsample.features.main

import androidx.lifecycle.*
import com.kyungeun.offlinecachingsample.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val loadData = MutableLiveData(Unit)

    val products = loadData.switchMap {
        repository.getProducts().asLiveData()
    }

    fun deleteAll() {
        viewModelScope.launch{
            repository.deleteAllProducts()
        }
    }

    fun refresh() {
        loadData.value = Unit
    }
}