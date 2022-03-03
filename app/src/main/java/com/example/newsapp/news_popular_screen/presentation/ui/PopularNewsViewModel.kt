package com.example.newsapp.news_popular_screen.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.data.models.Category
import com.example.newsapp.news_popular_screen.data.models.Country
import com.example.newsapp.news_popular_screen.domain.usecase.GetCategoryNewsUseCase
import com.example.newsapp.news_popular_screen.domain.usecase.GetCountryNewsUseCase
import com.example.newsapp.news_popular_screen.domain.usecase.PopularPagerSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PopularNewsViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryNewsUseCase,
    private val getCountryUseCase: GetCountryNewsUseCase,
    private val pageSourceCreateUseCase: PopularPagerSourceUseCase,
) : ViewModel() {

    private val _category: MutableLiveData<CategoryResponse> = MutableLiveData()
    private val _country: MutableLiveData<CountryResponse> = MutableLiveData()
    private val _query: MutableLiveData<String> = MutableLiveData()

    init {
        _country.value = CountryResponse.ru
        _category.value = CategoryResponse.business
        _query.value = ""
    }

    val newsCategoryFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _category.asFlow()
            .flatMapLatest { category ->
                pageSourceCreateUseCase.execute(country = _country.value!!,
                    category = category,
                    query = _query.value!!)
            }
            .cachedIn(viewModelScope)
    }
    val newsCountryFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _country.asFlow()
            .flatMapLatest { country ->
                pageSourceCreateUseCase.execute(country = country,
                    category = _category.value!!, query = _query.value!!)
            }
            .cachedIn(viewModelScope)
    }
    val newsQueryFlow: Flow<PagingData<Article>> by lazy(LazyThreadSafetyMode.NONE) {
        _query.asFlow()
            .flatMapLatest { query ->
                pageSourceCreateUseCase.execute(country = _country.value!!,
                    category = _category.value!!, query = query)
            }
            .cachedIn(viewModelScope)
    }

    fun getCategoryResponse(category: CategoryResponse) { _category.value = category }

    fun getCountryResponse(country: CountryResponse) { _country.value = country }

    fun getQueryResponse(query: String) { _query.value = query }

    fun getCategory(): List<Category> = getCategoryUseCase.execute()

    fun getCountry(): List<Country> = getCountryUseCase.execute()


}