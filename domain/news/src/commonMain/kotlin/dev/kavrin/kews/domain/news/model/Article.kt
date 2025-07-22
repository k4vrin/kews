package dev.kavrin.kews.domain.news.model


data class Article(
    val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val source: Source,
    val author: String?,
)


data class Source(
    val id: String?,
    val name: String,
)


data class ArticlesResponse(
    val articles: List<Article>,
    val totalResults: Int,
    val currentPage: Int,
    val totalPages: Int,
)