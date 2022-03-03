package com.example.newsapp.app

import com.example.newsapp.data.models.ArticleDto
import com.example.newsapp.data.models.SourceDto
import com.example.newsapp.domain.models.Article
import com.example.newsapp.domain.models.Source

internal fun ArticleDto.toArticle(): Article {
    return Article(
        source = this.source?.toSource(),
        title = title,
        url = url,
        description = description,
        author = author,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

private fun SourceDto.toSource(): Source {
    return Source(id = id, name = name)
}
