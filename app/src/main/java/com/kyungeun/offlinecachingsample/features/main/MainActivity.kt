package com.kyungeun.offlinecachingsample.features.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.kyungeun.offlinecachingsample.R
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
                if(result.data != null) {
                    adapter.submitList(result.data)
                }

                when (result) {
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        errorTv.isVisible = false
                    }
                    is Resource.Loading -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = true
                        }
                    }
                    is Resource.Error -> {
                        if (result.data.isNullOrEmpty()) {
                            progressBar.isVisible = false
                            errorTv.isVisible = true
                            errorTv.text = result.error?.localizedMessage
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_cache -> {
                viewModel.deleteAll()
            }
            R.id.action_refresh -> {
                viewModel.refresh()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}