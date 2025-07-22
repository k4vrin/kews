package dev.kavrin.kews.decompose.news.articles

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.kavrin.kews.news.ArticleUI
import dev.kavrin.kews.news.ArticlesResponseUI
import dev.kavrin.kews.presentation.mappers.toUI
import dev.kavrin.kews.domain.news.usecase.GetArticleByIdUseCase
import dev.kavrin.kews.domain.news.usecase.GetTopHeadlinesUseCase
import dev.kavrin.kews.domain.news.usecase.SearchArticlesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class DefaultArticlesComponent(
    componentContext: ComponentContext,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
) : ArticlesComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _articlesState = MutableValue(ArticlesState())
    override val articlesState: Value<ArticlesState> = _articlesState

    override suspend fun loadTopHeadlines(page: Int): Result<ArticlesResponseUI> {
        _articlesState.value = _articlesState.value.copy(isLoading = true, error = null)

        return try {
            val result = getTopHeadlinesUseCase(page = page)
            result.fold(
                onSuccess = { response ->
                    val responseUI = response.toUI()
                    updateArticles(responseUI.articleUIS)
                    _articlesState.value = _articlesState.value.copy(isLoading = false)
                    Result.success(responseUI)
                },
                onFailure = { error ->
                    _articlesState.value = _articlesState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            _articlesState.value = _articlesState.value.copy(
                isLoading = false,
                error = e.message
            )
            Result.failure(e)
        }
    }

    override suspend fun searchArticles(query: String, page: Int): Result<ArticlesResponseUI> {
        _articlesState.value = _articlesState.value.copy(isLoading = true, error = null)

        return try {
            val result = searchArticlesUseCase(query = query, page = page)
            result.fold(
                onSuccess = { response ->
                    val responseUI = response.toUI()
                    updateArticles(responseUI.articleUIS)
                    _articlesState.value = _articlesState.value.copy(isLoading = false)
                    Result.success(responseUI)
                },
                onFailure = { error ->
                    _articlesState.value = _articlesState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            _articlesState.value = _articlesState.value.copy(
                isLoading = false,
                error = e.message
            )
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: String): ArticleUI? {
        return _articlesState.value.articles[id]
    }

    override suspend fun fetchArticleById(id: String): Result<ArticleUI> {
        // First check if we already have it cached
        getArticleById(id)?.let { return Result.success(it) }

        // If not cached, fetch from repository
        return try {
            val result = getArticleByIdUseCase(id)
            result.fold(
                onSuccess = { article ->
                    val articleUI = article.toUI()
                    updateArticles(listOf(articleUI))
                    Result.success(articleUI)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun updateArticles(articles: List<ArticleUI>) {
        val currentArticles = _articlesState.value.articles.toMutableMap()
        articles.forEach { article ->
            currentArticles[article.id] = article
        }
        _articlesState.value = _articlesState.value.copy(articles = currentArticles)
    }

    override fun clearArticles() {
        _articlesState.value = _articlesState.value.copy(articles = emptyMap())
    }
}
