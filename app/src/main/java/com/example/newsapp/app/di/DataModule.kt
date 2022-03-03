package com.example.newsapp.app.di

import com.example.newsapp.news_all_screen.data.repository.AllNewsRepositoryImpl
import com.example.newsapp.news_all_screen.domain.repository.AllNewsRepository
import com.example.newsapp.news_popular_screen.data.repository.PopularNewsRepositoryImpl
import com.example.newsapp.news_popular_screen.domain.repository.PopularNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideNewsRepository(): PopularNewsRepository = PopularNewsRepositoryImpl()

    @Provides
    @Singleton
    fun provideAllNewsRepository(): AllNewsRepository = AllNewsRepositoryImpl()


}