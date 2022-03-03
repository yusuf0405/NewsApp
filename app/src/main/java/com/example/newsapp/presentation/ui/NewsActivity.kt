package com.example.newsapp.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.data.api.CategoryResponse
import com.example.newsapp.data.api.CountryResponse
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.domain.models.Article
import com.example.newsapp.presentation.adapters.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class NewsActivity : AppCompatActivity(), ItemOnClickListener,
    SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val newsAdapter: NewsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter(this)
    }
    private val categoryAdapter: CategoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoryAdapter(this)
    }
    private val countryAdapter: CountryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CountryAdapter(this)
    }

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        bindRecaycleViews()

        binding.searchView.setOnQueryTextListener(this)
        binding.swiperefresh.setOnRefreshListener(this)
        binding.swiperefresh.setColorSchemeResources(
            R.color.green,
            R.color.green,
            R.color.green,
            R.color.green)



        readingData(flow = viewModel.newsCategoryFlow)
        readingData(flow = viewModel.newsCountryFlow)
        readingData(flow = viewModel.newsQueryFlow)

        binding.newsRv.adapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter(),
            footer = NewsLoadStateAdapter()
        )
        newsAdapter.addLoadStateListener { state ->
            binding.apply {
                newsRv.isVisible = state.refresh != LoadState.Loading
                progressDialog.isVisible = state.refresh == LoadState.Loading
            }
            if (state.refresh is LoadState.Error) {
                Snackbar.make(binding.root,
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun categoryOnClick(categoryType: CategoryResponse) =
        viewModel.getCategoryResponse(categoryType)

    override fun countryOnClick(countryType: CountryResponse) =
        viewModel.getCountryResponse(countryType)

    override fun showNewsToUrl(url: String) {
        intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onRefresh() {
        viewModel.getQueryResponse("")
        binding.swiperefresh.isRefreshing = true
        binding.swiperefresh.postDelayed({
            binding.swiperefresh.isRefreshing = false
        }, 500)
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) {
            viewModel.getQueryResponse(query = searchText)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText!!.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            viewModel.getQueryResponse(query = searchText)
        } else {
            viewModel.getQueryResponse("")
        }
        return false
    }

    private fun bindRecaycleViews() {
        binding.categoryRv.adapter = categoryAdapter
        binding.countryRv.adapter = countryAdapter
        categoryAdapter.categoryList = viewModel.getCategory()
        countryAdapter.countryList = viewModel.getCountry()
    }

    private fun readingData(flow: Flow<PagingData<Article>>) {
        GlobalScope.launch(Dispatchers.Main) {
            flow.collectLatest(newsAdapter::submitData)
        }
    }
}