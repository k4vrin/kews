package dev.kavrin.kews.domain.news.repository

import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.domain.news.model.ArticlesResponse

interface NewsRepository {
    suspend fun getTopHeadlines(
        country: String = "us",
        category: String = "general",
        page: Int = 1,
        pageSize: Int = 20
    ): Result<ArticlesResponse>

    suspend fun searchArticles(
        query: String,
        page: Int = 1,
        pageSize: Int = 20,
        sortBy: String = "publishedAt"
    ): Result<ArticlesResponse>

    suspend fun getArticleById(id: String): Result<Article>
}