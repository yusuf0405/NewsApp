package com.example.newsapp.news_all_screen.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.app.toArticle
import com.example.newsapp.app.utils.Cons.Companion.MAX_PAGE_SIZE
import com.example.newsapp.news_all_screen.domain.repository.AllNewsRepository
import com.example.newsapp.news_popular_screen.data.api.LanguageResponse
import com.example.newsapp.news_popular_screen.data.api.SortByResponse
import com.example.newsapp.news_popular_screen.domain.models.Article
import retrofit2.HttpException

class AllNewsPagingSource(
    private val api: AllNewsRepository,
    private val language: LanguageResponse,
    private val sortBy: SortByResponse,
    private val query: String,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
        return try {
            val response = api.getAllNews(
                language = language,
                sortBy = sortBy,
                query = query,
                page = page,
                pageSize = pageSize)
            if (response.isSuccessful) {
                val article = response.body()!!.articles.map { it.toArticle() }
                val nextKey = if (article.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(article, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}