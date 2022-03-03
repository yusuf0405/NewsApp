package com.example.newsapp.news_popular_screen.domain.usecse

import androidx.paging.PagingData
import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularPagerSourceUseCase @Inject constructor(private val repository: PopularNewsRepository) {
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