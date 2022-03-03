package com.example.newsapp.app.network.models

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("source") val source: SourceDto? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null,
)