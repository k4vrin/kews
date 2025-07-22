package dev.kavrin.kews.presentation.mappers

import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.domain.news.model.Source
import dev.kavrin.kews.domain.news.model.ArticlesResponse
import dev.kavrin.kews.news.ArticleUI
import dev.kavrin.kews.news.SourceUI
import dev.kavrin.kews.news.ArticlesResponseUI

fun Article.toUI(): ArticleUI = ArticleUI(
    id = id,
    title = title,
    description = description,
    content = content,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    sourceUI = source.toUI(),
    author = author
)

fun Source.toUI(): SourceUI = SourceUI(
    id = id,
    name = name
)

fun ArticlesResponse.toUI(): ArticlesResponseUI = ArticlesResponseUI(
    articleUIS = articles.map { it.toUI() },
    totalResults = totalResults,
    currentPage = currentPage,
    totalPages = totalPages
)

fun ArticleUI.toDomain(): Article = Article(
    id = id,
    title = title,
    description = description,
    content = content,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    source = sourceUI.toDomain(),
    author = author
)

fun SourceUI.toDomain(): Source = Source(
    id = id,
    name = name
)

fun ArticlesResponseUI.toDomain(): ArticlesResponse = ArticlesResponse(
    articles = articleUIS.map { it.toDomain() },
    totalResults = totalResults,
    currentPage = currentPage,
    totalPages = totalPages
)
