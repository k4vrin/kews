package dev.kavrin.kews.data.news.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)