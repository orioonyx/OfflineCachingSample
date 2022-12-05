package com.kyungeun.offlinecachingsample.features.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.databinding.ActivityMainBinding
import com.kyungeun.offlinecachingsample.features.detail.DetailActivity
import com.kyungeun.offlinecachingsample.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter = ProductAdapter(object : ProductAdapter.OnItemClickListener {
                override fun onClickedItem(product: Product) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra("id", product.id)
                    startActivity(intent)
                }
            })
            recyclerView.adapter = adapter
        }
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.products.observe(this@MainActivity) { result ->

                adapter.submitList(result.data)

                when (result) {
                    is Resource.Error -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = false
                            errorTv.isVisible = true
                            errorTv.text = result.error?.localizedMessage
                        }
                    }

                    is Resource.Loading -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = true
                        }
                    }

                    is Resource.Success -> {
                        progressBar.isVisible = false
                        errorTv.isVisible = false
                    }
                }
            }
        }
    }
}