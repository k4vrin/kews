package dev.kavrin.kews.news

import kotlinx.serialization.Serializable

data class NewsListState(
    val articleUIS: List<ArticleUI> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val hasMore: Boolean = false,
)

@Serializable
sealed class NewsListEvent {
    data object Refresh : NewsListEvent()
    data object LoadMore : NewsListEvent()
    data class Search(val query: String) : NewsListEvent()
    data object ClearSearch : NewsListEvent()
    data object BackClick : NewsListEvent()
    data class ArticleClicked(val articleUI: ArticleUI) : NewsListEvent()
}

@Serializable
sealed interface NewsListSideEffect {
    @Serializable
    data class ShowError(val message: String) : NewsListSideEffect
    @Serializable
    data class NavigateToArticleDetails(val articleUI: ArticleUI) : NewsListSideEffect
}


@Serializable
data class ArticleUI(
    val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val sourceUI: SourceUI,
    val author: String?,
)

@Serializable
data class SourceUI(
    val id: String?,
    val name: String,
)


@Serializable
data class ArticlesResponseUI(
    val articleUIS: List<ArticleUI>,
    val totalResults: Int,
    val currentPage: Int,
    val totalPages: Int,
)