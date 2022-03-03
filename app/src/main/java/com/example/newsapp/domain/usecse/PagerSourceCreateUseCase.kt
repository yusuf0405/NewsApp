package com.example.newsapp.domain.usecse

import androidx.paging.PagingData
import com.example.newsapp.data.api.CategoryResponse
import com.example.newsapp.data.api.CountryResponse
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagerSourceCreateUseCase @Inject constructor(private val repository: NewsRepository) {
    fun execute(
        country: CountryResponse,
        category: CategoryResponse,
        query: String,
    ): Flow<PagingData<Article>> =
        repository.pagerSourceCreate(
            country = country,
            category = category,
            query = query
        )

}