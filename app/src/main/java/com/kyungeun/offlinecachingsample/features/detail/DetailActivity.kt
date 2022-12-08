package com.kyungeun.offlinecachingsample.features.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.kyungeun.offlinecachingsample.R
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.databinding.ActivityDetailBinding
import com.kyungeun.offlinecachingsample.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.start(intent.getIntExtra("id", -1))

        setupObservers()
    }

    private fun setupObservers() {
        binding.apply {
            viewModel.product.observe(this@DetailActivity) { result ->
                if (result.data != null) {
                    updateUI(result.data)
                }
                when (result) {
                    is Resource.Success -> {
                        progressBar.isVisible = false
                        errorTv.isVisible = false
                    }
                    is Resource.Loading -> {
                        if (result.data == null) {
                            progressBar.isVisible = true
                        }
                    }
                    is Resource.Error -> {
                        progressBar.isVisible = false
                        errorTv.isVisible = true

                        errorTv.text = if (result.error?.message != null) {
                            result.error.message
                        } else {
                            getString(R.string.error_msg)
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(data: Product) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(data.image)
                .fitCenter()
                .override(512, 512)
                .dontAnimate()
                .error(R.drawable.ic_baseline_android_24)
                .into(productIv)

            titleTv.text = data.title
            categoryTv.text = data.category
            priceTv.text = data.price.toString()
            descriptionTv.text = data.description
        }
    }
}