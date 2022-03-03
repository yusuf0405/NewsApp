package com.example.newsapp.news_all_screen.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.app.network.RetrofitInstance
import com.example.newsapp.app.network.models.ArticlesResponseDto
import com.example.newsapp.news_all_screen.data.models.Language
import com.example.newsapp.news_all_screen.data.models.SortBy
import com.example.newsapp.news_all_screen.data.source.AllNewsPagingSource
import com.example.newsapp.news_all_screen.domain.repository.AllNewsRepository
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AllNewsRepositoryImpl : AllNewsRepository {
    override suspend fun getAllNews(
        language: LanguageResponse,
        sortBy: SortByResponse,
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<ArticlesResponseDto> =
        RetrofitInstance.newsApi.everything(
            query = query,
            language = language,
            sortBy = sortBy,
            pageSize = pageSize,
            page = page
        )

    override fun pagerSourceCreate(
        language: LanguageResponse,
        sortBy: SortByResponse,
        query: String,
    ): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AllNewsPagingSource(api = this,
                    language = language,
                    sortBy = sortBy,
                    query = query)
            }
        ).flow

    override fun getLanguageNews(): List<Language> {
        val languageList = mutableListOf<Language>()
        languageList.add(Language("Russia", LanguageResponse.ru, true))
        languageList.add(Language("Argentina", LanguageResponse.ar, false))
        languageList.add(Language("Germany", LanguageResponse.de, false))
        languageList.add(Language("English", LanguageResponse.en, false))
        languageList.add(Language("Spain", LanguageResponse.es, false))
        languageList.add(Language("France", LanguageResponse.fr, false))
        languageList.add(Language("Italy", LanguageResponse.it, false))
        languageList.add(Language("Portuguese", LanguageResponse.pt, false))
        languageList.add(Language("Chinese", LanguageResponse.zh, false))

        return languageList
    }

    override fun getSortByNews(): List<SortBy> {
        val sortByList = mutableListOf<SortBy>()
        sortByList.add(SortBy("Popularity", SortByResponse.popularity, true))
        sortByList.add(SortBy("PublishedAt", SortByResponse.publishedAt, false))
        sortByList.add(SortBy("Relevancy", SortByResponse.relevancy, false))
        return sortByList
    }
}