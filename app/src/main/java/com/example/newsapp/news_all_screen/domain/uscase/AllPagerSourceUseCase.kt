package com.example.newsapp.news_all_screen.domain.uscase

import androidx.paging.PagingData
import com.example.newsapp.news_all_screen.domain.repository.AllNewsRepository
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AllPagerSourceUseCase @Inject constructor(private val repository: AllNewsRepository) {
    fun execute(
        language: LanguageResponse,
        sortBy: SortByResponse,
        query: String,
    ): Flow<PagingData<Article>> =
        repository.pagerSourceCreate(
            language = language,
            sortBy = sortBy,
            query = query
        )

}