package dev.kavrin.kews.data.news.repository

import dev.kavrin.kews.data.news.mapper.toDomain
import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.domain.news.model.ArticlesResponse
import dev.kavrin.kews.data.news.remote.api.NewsApiService
import dev.kavrin.kews.domain.news.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String,
        page: Int,
        pageSize: Int
    ): Result<ArticlesResponse> {
        return try {
            val response = newsApiService.getTopHeadlines(country, category, page, pageSize)
            Result.success(response.toDomain(page, pageSize))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchArticles(
        query: String,
        page: Int,
        pageSize: Int,
        sortBy: String
    ): Result<ArticlesResponse> {
        return try {
            val response = newsApiService.searchArticles(query, page, pageSize, sortBy)
            Result.success(response.toDomain(page, pageSize))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: String): Result<Article> {
        return try {
            // For simplicity, we'll search for the article by URL (which we use as ID)
            val response = newsApiService.searchArticles(query = id, pageSize = 1)
            val article = response.articles.firstOrNull()?.toDomain()
            if (article != null) {
                Result.success(article)
            } else {
                Result.failure(Exception("Article not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
