package com.example.newsapp.domain.usecse

import com.example.newsapp.domain.models.Category
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetCategoryNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    fun execute(): List<Category> = repository.getAllCategory()
}