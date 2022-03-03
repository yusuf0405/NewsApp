package com.example.newsapp.domain.usecse

import com.example.newsapp.domain.models.Country
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetCountryNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    fun execute(): List<Country> = repository.getAllCountry()

}