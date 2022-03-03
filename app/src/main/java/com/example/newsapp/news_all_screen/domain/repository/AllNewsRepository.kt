package com.example.newsapp.news_all_screen.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.app.network.models.ArticlesResponseDto
import com.example.newsapp.news_all_screen.data.models.Language
import com.example.newsapp.news_all_screen.data.models.SortBy
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AllNewsRepository {
    suspend fun getAllNews(
        language: LanguageResponse,
        sortBy: SortByResponse,
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<ArticlesResponseDto>

    fun pagerSourceCreate(
        language: LanguageResponse,
        sortBy: SortByResponse,
        query: String,
    ): Flow<PagingData<Article>>

    fun getLanguageNews(): List<Language>

    fun getSortByNews(): List<SortBy>
}