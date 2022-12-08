package com.kyungeun.offlinecachingsample.features.detail

import androidx.lifecycle.*
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.data.repository.ProductRepository
import com.kyungeun.offlinecachingsample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _product = _id.switchMap { id ->
        repository.getProduct(id).asLiveData()
    }

    val product: LiveData<Resource<Product>> = _product


    fun start(id: Int) {
        _id.value = id
    }
}
