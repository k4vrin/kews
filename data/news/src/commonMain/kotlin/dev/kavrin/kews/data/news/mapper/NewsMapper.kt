package dev.kavrin.kews.data.news.mapper

import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.domain.news.model.ArticlesResponse
import dev.kavrin.kews.domain.news.model.Source
import dev.kavrin.kews.data.news.remote.dto.ArticleDto
import dev.kavrin.kews.data.news.remote.dto.NewsApiResponse
import dev.kavrin.kews.data.news.remote.dto.SourceDto

fun NewsApiResponse.toDomain(page: Int, pageSize: Int): ArticlesResponse {
    val totalPages = (totalResults + pageSize - 1) / pageSize
    return ArticlesResponse(
        articles = articles.map { it.toDomain() },
        totalResults = totalResults,
        currentPage = page,
        totalPages = totalPages
    )
}

fun ArticleDto.toDomain(): Article {
    return Article(
        id = url, // Using URL as ID since NewsAPI doesn't provide unique IDs
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        source = source.toDomain(),
        author = author
    )
}

fun SourceDto.toDomain(): Source {
    return Source(
        id = id,
        name = name
    )
}
