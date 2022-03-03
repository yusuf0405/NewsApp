package com.example.newsapp.news_popular_screen.domain.usecase

import com.example.newsapp.news_popular_screen.data.models.Country
import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository
import javax.inject.Inject

class GetCountryNewsUseCase @Inject constructor(private val repository: PopularNewsRepository) {
    fun execute(): List<Country> = repository.getAllCountry()

}