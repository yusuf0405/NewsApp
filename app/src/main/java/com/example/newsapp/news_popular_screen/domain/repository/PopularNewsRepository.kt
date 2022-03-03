package com.example.newsapp.news_popular_screen.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.app.network.models.ArticlesResponseDto
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.data.models.Category
import com.example.newsapp.news_popular_screen.data.models.Country
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PopularNewsRepository {
    suspend fun getTopHeaders(
        country: CountryResponse,
        category: CategoryResponse,
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<ArticlesResponseDto>

    fun pagerSourceCreate(
        country: CountryResponse,
        category: CategoryResponse,
        query: String,
    ): Flow<PagingData<Article>>

    fun getAllCategory(): List<Category>

    fun getAllCountry(): List<Country>


}