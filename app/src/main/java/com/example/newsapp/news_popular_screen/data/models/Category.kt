package com.example.newsapp.news_popular_screen.data.models

import com.example.newsapp.news_popular_screen.data.api.CategoryResponse

data class Category(
    val title: String,
    val category: CategoryResponse,
    var isClick: Boolean,
)
