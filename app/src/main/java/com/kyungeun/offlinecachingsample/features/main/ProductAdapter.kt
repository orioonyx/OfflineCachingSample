package com.kyungeun.offlinecachingsample.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kyungeun.offlinecachingsample.R
import com.kyungeun.offlinecachingsample.data.model.Product
import com.kyungeun.offlinecachingsample.databinding.ItemProductBinding

class ProductAdapter(private val listener: OnItemClickListener) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF) {

    interface OnItemClickListener {
        fun onClickedItem(product: Product)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductViewHolder(private val binding: ItemProductBinding, private val listener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

        private lateinit var product: Product

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(model: Product) {
            this.product = model
            binding.apply {
                Glide.with(root)
                    .load(model.image)
                    .fitCenter()
                    .override(256, 256)
                    .dontAnimate()
                    .error(R.drawable.ic_baseline_android_24)
                    .into(image)

                title.text = model.title
                category.text = model.category
                price.text = model.price.toString()
                description.text = model.description
            }
        }

        override fun onClick(p0: View?) {
            listener.onClickedItem(product)
        }
    }

}