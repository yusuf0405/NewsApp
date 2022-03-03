package com.example.newsapp.news_popular_screen.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.app.utils.NewsLoadStateAdapter
import com.example.newsapp.databinding.PopularNewsFragmentBinding
import com.example.newsapp.news_popular_screen.data.api.CategoryResponse
import com.example.newsapp.news_popular_screen.data.api.CountryResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.presentation.adapters.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class PopularNewsFragment : Fragment(), PopularItemOnClickListener,
    SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    private val binding: PopularNewsFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        PopularNewsFragmentBinding.inflate(layoutInflater)
    }
    private val newsAdapter: PopularNewsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PopularNewsAdapter(this)
    }
    private val categoryAdapter: CategoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CategoryAdapter(this)
    }
    private val countryAdapter: CountryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CountryAdapter(this)
    }

    private val viewModel: PopularNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun categoryOnClick(categoryType: CategoryResponse) =
        viewModel.getCategoryResponse(categoryType)

    override fun countryOnClick(countryType: CountryResponse) =
        viewModel.getCountryResponse(countryType)

    override fun showNewsToUrl(url: String) {
        activity?.intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(activity?.intent)
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