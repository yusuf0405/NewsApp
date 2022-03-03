package com.example.newsapp.news_all_screen.presentation.adapter

import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse

interface AllItemOnClickListener {
    fun languageOnClick(language: LanguageResponse)

    fun sortByOnClick(sortBy: SortByResponse)

    fun showNewsToUrl(url: String)
}