package com.example.newsapp.news_all_screen.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoryItemBinding
import com.example.newsapp.news_all_screen.data.models.SortBy


class SortByAdapter(private val actionListener: AllItemOnClickListener) :
    RecyclerView.Adapter<SortByAdapter.CategoryViewHolder>() {

    var sortByList: List<SortBy> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(sortBy: SortBy) {
            binding.apply {
                categoryTitle.text = sortBy.title
                if (sortBy.isClick) {
                    categoryItem.setCardBackgroundColor(Color.GREEN)
                } else {
                    categoryItem.setCardBackgroundColor(Color.WHITE)
                }
            }
            if (!sortBy.isClick) {
                itemView.setOnClickListener {
                    sortByList.forEach {
                        it.isClick = false
                    }
                    sortBy.isClick = true
                    actionListener.sortByOnClick(sortBy = sortBy.sortBy)
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
        holder.bind(sortBy = sortByList[position])
    }

    override fun getItemCount(): Int = sortByList.size
}

