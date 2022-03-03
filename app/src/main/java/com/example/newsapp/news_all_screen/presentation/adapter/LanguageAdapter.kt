package com.example.newsapp.news_all_screen.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.CategoryItemBinding
import com.example.newsapp.news_all_screen.data.models.Language


class LanguageAdapter(private val actionListener: AllItemOnClickListener) :
    RecyclerView.Adapter<LanguageAdapter.CategoryViewHolder>() {

    var languageList: List<Language> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(language: Language) {
            binding.apply {
                categoryTitle.text = language.title
                if (language.isClick) {
                    categoryItem.setCardBackgroundColor(Color.GREEN)
                } else {
                    categoryItem.setCardBackgroundColor(Color.WHITE)
                }
            }
            if (!language.isClick) {
                itemView.setOnClickListener {
                    languageList.forEach {
                        it.isClick = false
                    }
                    language.isClick = true
                    actionListener.languageOnClick(language = language.language)
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
        holder.bind(language = languageList[position])
    }

    override fun getItemCount(): Int = languageList.size
}

