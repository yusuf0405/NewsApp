package com.example.newsapp.presentation.adapters

import com.example.newsapp.data.api.CategoryResponse
import com.example.newsapp.data.api.CountryResponse

interface ItemOnClickListener {
    fun categoryOnClick(categoryType: CategoryResponse)
    fun countryOnClick(countryType: CountryResponse)
    fun showNewsToUrl(url: String)
}