package com.kyungeun.offlinecachingsample.features.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kyungeun.offlinecachingsample.R
import com.kyungeun.offlinecachingsample.databinding.ActivityDetailBinding
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
                if (result != null) {
                    Glide.with(root)
                        .load(result.image)
                        .fitCenter()
                        .override(512, 512)
                        .dontAnimate()
                        .error(R.drawable.ic_baseline_android_24)
                        .into(productIv)

                    titleTv.text = result.title
                    categoryTv.text = result.category
                    priceTv.text = result.price.toString()
                    descriptionTv.text = result.description

                    errorTv.visibility = android.view.View.INVISIBLE
                } else {
                    errorTv.visibility = android.view.View.VISIBLE
                    errorTv.text = getString(R.string.error_msg)
                }
            }
        }
    }
}