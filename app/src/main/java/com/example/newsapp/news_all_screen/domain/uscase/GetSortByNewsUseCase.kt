package com.example.newsapp.news_all_screen.domain.uscase

import com.example.newsapp.news_all_screen.domain.repository.AllNewsRepository
import javax.inject.Inject

class GetSortByNewsUseCase @Inject constructor(private var repository: AllNewsRepository) {
    fun execute() = repository.getSortByNews()
}