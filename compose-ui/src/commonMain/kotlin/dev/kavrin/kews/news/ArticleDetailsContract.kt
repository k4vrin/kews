package dev.kavrin.kews.news

import com.arkivanov.decompose.value.Value

data class ArticleDetailState(
    val article: ArticleUI? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
sealed class ArticleDetailEvent {
    data object BackClicked : ArticleDetailEvent()
    data object ShareClicked : ArticleDetailEvent()
    data object OpenInBrowserClicked : ArticleDetailEvent()
}

interface ArticleDetailComponent {
    val state: Value<ArticleDetailState>
    fun onEvent(event: ArticleDetailEvent)
}