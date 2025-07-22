package dev.kavrin.kews.decompose.news.news_details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.kavrin.kews.decompose.news.articles.ArticlesComponent
import dev.kavrin.kews.news.ArticleDetailComponent
import dev.kavrin.kews.news.ArticleDetailEvent
import dev.kavrin.kews.news.ArticleDetailState
import dev.kavrin.kews.util.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultArticleDetailComponent(
    componentContext: ComponentContext,
    private val articleId: String,
    private val articlesComponent: ArticlesComponent,
    private val onBackClicked: () -> Unit,
    private val onShare: (String) -> Unit = {},
    private val onOpenInBrowser: (String) -> Unit = {},
) : ArticleDetailComponent, ComponentContext by componentContext {

    private val _state = MutableValue(ArticleDetailState())
    override val state: Value<ArticleDetailState> = _state

    private val coroutineScope = coroutineScope(SupervisorJob())

    init {
        loadArticle()
    }

    override fun onEvent(event: ArticleDetailEvent) {
        when (event) {
            ArticleDetailEvent.BackClicked -> onBackClicked()
            ArticleDetailEvent.ShareClicked -> {
                _state.value.article?.let { article ->
                    onShare(article.url)
                }
            }
            ArticleDetailEvent.OpenInBrowserClicked -> {
                _state.value.article?.let { article ->
                    onOpenInBrowser(article.url)
                }
            }
        }
    }

    private fun loadArticle() {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            // First try to get from cache
            val cachedArticle = articlesComponent.getArticleById(articleId)
            if (cachedArticle != null) {
                _state.update {
                    it.copy(
                        article = cachedArticle,
                        isLoading = false
                    )
                }
                return@launch
            }

            // If not in cache, fetch from repository
            val result = articlesComponent.fetchArticleById(articleId)
            result.fold(
                onSuccess = { article ->
                    _state.update {
                        it.copy(
                            article = article,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load article"
                        )
                    }
                }
            )
        }
    }
}