package com.example.newsapp.news_popular_screen.presentation.adapters

import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse

interface PopularItemOnClickListener {
    fun categoryOnClick(categoryType: CategoryResponse)
    fun countryOnClick(countryType: CountryResponse)
    fun showNewsToUrl(url: String)
}