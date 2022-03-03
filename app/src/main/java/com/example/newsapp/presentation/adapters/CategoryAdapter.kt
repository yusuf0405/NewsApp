package com.example.newsapp.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoryItemBinding
import com.example.newsapp.domain.models.Category


class CategoryAdapter(private val actionListener: ItemOnClickListener) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categoryList: List<Category> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: Category) {
            binding.apply {
                categoryTitle.text = category.title
                if (category.isClick) {
                    categoryItem.setCardBackgroundColor(Color.GREEN)
                } else {
                    categoryItem.setCardBackgroundColor(Color.WHITE)
                }
            }
            if (!category.isClick) {
                itemView.setOnClickListener {
                    categoryList.forEach {
                        it.isClick = false
                    }
                    category.isClick = true
                    actionListener.categoryOnClick(category.category)
                    notifyDataSetChanged()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        val binding = CategoryItemBinding.bind(layoutInflater)
        return CategoryViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(category = categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
}

