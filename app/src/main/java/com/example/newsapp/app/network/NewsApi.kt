package com.example.newsapp.news_popular_screen.data.api

import androidx.annotation.IntRange
import com.example.newsapp.app.network.models.ArticlesResponseDto
import com.example.newsapp.app.utils.Cons.Companion.DEFAULT_PAGE_SIZE
import com.example.newsapp.app.utils.Cons.Companion.MAX_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeaders(
        @Query("q") query: String? = null,
        @Query("country") country: CountryResponse,
        @Query("category") category: CategoryResponse,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
    ): Response<ArticlesResponseDto>

    @GET("/v2/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("domains") domains: String? = "bbc.com , euronews.com , edition.cnn.com , " +
                "news.google.com , aljazeera.com",
        @Query("pageSize") @IntRange(from = 1,
            to = MAX_PAGE_SIZE.toLong()) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("language") language: LanguageResponse? = null,
        @Query("sortBy") sortBy: SortByResponse? = null,
    ): Response<ArticlesResponseDto>

}

enum class LanguageResponse {
    ar,
    de,
    en,
    es,
    fr,
    he,
    it,
    nl,
    no,
    pt,
    ru,
    se,
    ud,
    zh,
}

enum class CountryResponse {
    ae,
    ar,
    at,
    au,
    be,
    bg,
    br,
    ca,
    ch,
    cn,
    co,
    cu,
    cz,
    de,
    eg,
    fr,
    gb,
    gr,
    hk,
    hu,
    id,
    ie,
    il,
    it,
    jp,
    kr,
    lt,
    lv,
    ma,
    mx,
    my,
    ng,
    nl,
    no,
    nz,
    ph,
    pl,
    pt,
    ro,
    rs,
    ru,
    sa,
    se,
    sg,
    si,
    sk,
    th,
    tr,
    tw,
    ua,
    us,
    ve,
    za
}

enum class CategoryResponse {
    business,
    entertainment,
    general,
    health,
    science,
    sports,
    technology
}

enum class SortByResponse {
    relevancy,
    popularity,
    publishedAt
}

