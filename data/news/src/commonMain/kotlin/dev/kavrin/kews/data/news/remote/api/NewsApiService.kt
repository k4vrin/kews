package dev.kavrin.kews.data.news.remote.api

import dev.kavrin.kews.data.news.remote.dto.NewsApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsApiService(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    companion object {
        const val BASE_URL = "https://newsapi.org/v2"
    }

    suspend fun getTopHeadlines(
        country: String = "us",
        category: String = "general",
        page: Int = 1,
        pageSize: Int = 20
    ): NewsApiResponse {
        return httpClient.get("$BASE_URL/top-headlines") {
            parameter("country", country)
            parameter("category", category)
            parameter("page", page)
            parameter("pageSize", pageSize)
            parameter("apiKey", apiKey)
        }.body()
    }

    suspend fun searchArticles(
        query: String,
        page: Int = 1,
        pageSize: Int = 20,
        sortBy: String = "publishedAt"
    ): NewsApiResponse {
        return httpClient.get("$BASE_URL/everything") {
            parameter("q", query)
            parameter("page", page)
            parameter("pageSize", pageSize)
            parameter("sortBy", sortBy)
            parameter("apiKey", apiKey)
        }.body()
    }
}