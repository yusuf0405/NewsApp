package com.example.newsapp.domain.models

import com.example.newsapp.data.api.CountryResponse

data class Country(
    val title: String,
    val country: CountryResponse,
    var isClick: Boolean,
)
