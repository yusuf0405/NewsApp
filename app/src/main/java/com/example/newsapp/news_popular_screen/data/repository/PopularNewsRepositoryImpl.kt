package com.example.newsapp.news_popular_screen.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.app.network.RetrofitInstance
import com.example.newsapp.app.network.models.ArticlesResponseDto
import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.data.models.Category
import com.example.newsapp.news_popular_screen.data.models.Country
import com.example.newsapp.news_popular_screen.data.source.NewsPagingSource
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PopularNewsRepositoryImpl : PopularNewsRepository {

    override suspend fun getTopHeaders(
        country: CountryResponse,
        category: CategoryResponse,
        query: String,
        page: Int,
        pageSize: Int,
    ): Response<ArticlesResponseDto> =
        RetrofitInstance.newsApi.getTopHeaders(query = query, country = country,
            category = category,
            page = page,
            pageSize = pageSize)

    override fun pagerSourceCreate(
        country: CountryResponse,
        category: CategoryResponse,
        query: String,
    ): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewsPagingSource(api = this,
                    country = country,
                    category = category,
                    query = query)
            }
        ).flow

    override fun getAllCategory(): List<Category> {
        val categoryList = mutableListOf<Category>()
        categoryList.add(Category("Business", CategoryResponse.business, true))
        categoryList.add(Category("Entertainment", CategoryResponse.entertainment, false))
        categoryList.add(Category("General", CategoryResponse.general, false))
        categoryList.add(Category("Health", CategoryResponse.health, false))
        categoryList.add(Category("Science", CategoryResponse.science, false))
        categoryList.add(Category("Sports", CategoryResponse.sports, false))
        categoryList.add(Category("Technology", CategoryResponse.technology, false))
        return categoryList

    }

    override fun getAllCountry(): List<Country> {
        val countryList = mutableListOf<Country>()
        countryList.add(Country("Russia", CountryResponse.ru, true))
        countryList.add(Country("United States", CountryResponse.us, false))
        countryList.add(Country("United Arab Emirates", CountryResponse.ae, false))
        countryList.add(Country("Argentina", CountryResponse.ar, false))
        countryList.add(Country("Austria", CountryResponse.at, false))
        countryList.add(Country("Australia", CountryResponse.au, false))
        countryList.add(Country("Belgium", CountryResponse.be, false))
        countryList.add(Country("Bulgaria", CountryResponse.bg, false))
        countryList.add(Country("Brazil", CountryResponse.br, false))
        countryList.add(Country("Republic of Ireland", CountryResponse.ie, false))
        countryList.add(Country("Illinois", CountryResponse.il, false))
        countryList.add(Country("Italy", CountryResponse.it, false))
        countryList.add(Country("Japan", CountryResponse.jp, false))
        countryList.add(Country("South Korea", CountryResponse.kr, false))
        countryList.add(Country("Lithuania", CountryResponse.lt, false))
        countryList.add(Country("Morocco", CountryResponse.ma, false))
        countryList.add(Country("Mexico", CountryResponse.mx, false))
        countryList.add(Country("Canada", CountryResponse.ca, false))
        countryList.add(Country("Switzerland", CountryResponse.ch, false))
        countryList.add(Country("China", CountryResponse.cn, false))
        countryList.add(Country("Czech Republic", CountryResponse.cz, false))
        countryList.add(Country("France", CountryResponse.fr, false))
        countryList.add(Country("United Kingdom", CountryResponse.gb, false))
        countryList.add(Country("Greece", CountryResponse.gr, false))
        countryList.add(Country("Hong Kong", CountryResponse.hk, false))
        return countryList
    }


}