package com.example.newsapp.domain.models

import com.example.newsapp.data.api.CategoryResponse

data class Category(
    val title: String,
    val category: CategoryResponse,
    var isClick: Boolean,
)
