package dev.kavrin.kews.decompose.news.articles

import com.arkivanov.decompose.value.Value
import dev.kavrin.kews.news.ArticleUI
import dev.kavrin.kews.news.ArticlesResponseUI

interface ArticlesComponent {
    val articlesState: Value<ArticlesState>

    suspend fun loadTopHeadlines(page: Int = 1): Result<ArticlesResponseUI>
    suspend fun searchArticles(query: String, page: Int = 1): Result<ArticlesResponseUI>
    suspend fun getArticleById(id: String): ArticleUI?
    suspend fun fetchArticleById(id: String): Result<ArticleUI>

    fun updateArticles(articles: List<ArticleUI>)
    fun clearArticles()
}

data class ArticlesState(
    val articles: Map<String, ArticleUI> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
