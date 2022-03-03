package com.example.newsapp.news_all_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.news_all_screen.data.models.Language
import com.example.newsapp.news_all_screen.data.models.SortBy
import com.example.newsapp.news_all_screen.domain.uscase.AllPagerSourceUseCase
import com.example.newsapp.news_all_screen.domain.uscase.GetLanguageNewsUseCase
import com.example.newsapp.news_all_screen.domain.uscase.GetSortByNewsUseCase
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class AllNewsViewModel @Inject constructor(
    private val allPagerSourceUseCase: AllPagerSourceUseCase,
    private val getLanguageNewsUseCase: GetLanguageNewsUseCase,
    private val getSortByNewsUseCase: GetSortByNewsUseCase,
) : ViewModel() {
    private val _language: MutableLiveData<LanguageResponse> = MutableLiveData()
    private val _sortBy: MutableLiveData<SortByResponse> = MutableLiveData()
    private val _query: MutableLiveData<String> = MutableLiveData()

    init {
        _sortBy.value = SortByResponse.popularity
        _language.value = LanguageResponse.ru
        _query.value = ""
    }

    val newsLanguageFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _language.asFlow()
            .flatMapLatest { language ->
                allPagerSourceUseCase.execute(language = language,
                    sortBy = _sortBy.value!!,
                    query = _query.value!!)
            }
            .cachedIn(viewModelScope)
    }
    val newsSortByFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _sortBy.asFlow()
            .flatMapLatest { sortBy ->
                allPagerSourceUseCase.execute(language = _language.value!!,
                    sortBy = sortBy,
                    query = _query.value!!)
            }
            .cachedIn(viewModelScope)
    }

    val newsQueryFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _query.asFlow()
            .flatMapLatest { query ->
                allPagerSourceUseCase.execute(language = _language.value!!,
                    sortBy = _sortBy.value!!,
                    query = query)
            }
            .cachedIn(viewModelScope)
    }

    fun getLanguageResponse(language: LanguageResponse) {
        _language.value = language
    }

    fun getSortByResponse(sortBy: SortByResponse) {
        _sortBy.value = sortBy
    }

    fun getQueryResponse(query: String) {
        _query.value = query
    }

    fun getSortBy(): List<SortBy> = getSortByNewsUseCase.execute()

    fun getLanguage(): List<Language> = getLanguageNewsUseCase.execute()

}