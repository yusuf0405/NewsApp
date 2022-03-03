package com.example.newsapp.news_all_screen.data.models

import com.example.newsapp.news_popular_screen.data.api.SortByResponse

data class SortBy(
    val title: String,
    val sortBy: SortByResponse,
    var isClick: Boolean,
)