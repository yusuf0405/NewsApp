package com.example.newsapp.news_popular_screen.data.models

import com.example.newsapp.news_popular_screen.data.api.CountryResponse

data class Country(
    val title: String,
    val country: CountryResponse,
    var isClick: Boolean,
)
