package com.example.newsapp.news_popular_screen.domain.usecse

import com.example.newsapp.news_popular_screen.data.models.Category
import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository
import javax.inject.Inject

class GetCategoryNewsUseCase @Inject constructor(private val repository: PopularNewsRepository) {
    fun execute(): List<Category> = repository.getAllCategory()
}