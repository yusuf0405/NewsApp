package com.example.newsapp.news_all_screen.data.models

import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse

data class Language(
    val title: String,
    val language: LanguageResponse,
    var isClick: Boolean,
)
