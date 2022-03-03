package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.api.CategoryResponse
import com.example.newsapp.data.api.CountryResponse
import com.example.newsapp.data.models.ArticlesResponseDto
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.Category
import com.example.newsapp.domain.models.Country
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
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