package com.example.newsapp.news_all_screen.presentation.ui

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
import com.example.newsapp.databinding.AllNewsFragmentBinding
import com.example.newsapp.news_all_screen.presentation.adapter.AllItemOnClickListener
import com.example.newsapp.news_all_screen.presentation.adapter.AllNewsAdapter
import com.example.newsapp.news_all_screen.presentation.adapter.LanguageAdapter
import com.example.newsapp.news_all_screen.presentation.adapter.SortByAdapter
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import com.example.newsapp.news_popular_screen.presentation.adapters.NewsLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class AllNewsFragment : Fragment(), AllItemOnClickListener, SwipeRefreshLayout.OnRefreshListener,
    SearchView.OnQueryTextListener {


    private val binding: AllNewsFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        AllNewsFragmentBinding.inflate(layoutInflater)
    }
    private val newsAdapter: AllNewsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        AllNewsAdapter(this)
    }
    private val sortByAdapter: SortByAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SortByAdapter(this)
    }
    private val languageAdapter: LanguageAdapter by lazy(LazyThreadSafetyMode.NONE) {
        LanguageAdapter(this)
    }

    private val viewModel: AllNewsViewModel by viewModels()
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



        readingData(flow = viewModel.newsLanguageFlow)
        readingData(flow = viewModel.newsSortByFlow)
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


    override fun languageOnClick(language: LanguageResponse) {
        viewModel.getLanguageResponse(language = language)
    }

    override fun sortByOnClick(sortBy: SortByResponse) {
        viewModel.getSortByResponse(sortBy = sortBy)
    }

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
        binding.languageRv.adapter = languageAdapter
        binding.sortByRv.adapter = sortByAdapter
        languageAdapter.languageList = viewModel.getLanguage()
        sortByAdapter.sortByList = viewModel.getSortBy()
    }

    private fun readingData(flow: Flow<PagingData<Article>>) {
        GlobalScope.launch(Dispatchers.Main) {
            flow.collectLatest(newsAdapter::submitData)
        }
    }

}