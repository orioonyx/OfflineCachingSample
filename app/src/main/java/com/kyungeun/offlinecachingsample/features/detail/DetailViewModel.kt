package com.kyungeun.offlinecachingsample.features.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _product = _id.switchMap { id ->
        repository.getProduct(id)
    }

    val product: LiveData<Product> = _product


    fun start(id: Int) {
        _id.value = id
    }
}
