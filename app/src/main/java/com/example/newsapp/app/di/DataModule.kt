package com.example.newsapp.app.di

import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
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
    fun provideNewsRepository(): NewsRepository = NewsRepositoryImpl()

}