package dev.kavrin.kews.decompose.news.news_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.kavrin.kews.decompose.news.articles.ArticlesComponent
import dev.kavrin.kews.news.NewsListEvent
import dev.kavrin.kews.news.NewsListState
import dev.kavrin.kews.util.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


interface NewsListComponent {
    val state: Value<NewsListState>
    fun onEvent(event: NewsListEvent)
}

class DefaultNewsListComponent(
    componentContext: ComponentContext,
    private val articlesComponent: ArticlesComponent,
    private val onArticleClicked: (String) -> Unit,
    private val onBackClicked: () -> Unit
) : NewsListComponent, ComponentContext by componentContext {

    private val _state = MutableValue(NewsListState())
    override val state: Value<NewsListState> = _state

    private val coroutineScope = coroutineScope(SupervisorJob())
    private var searchJob: Job? = null

    init {
        loadInitialArticles()
    }

    override fun onEvent(event: NewsListEvent) {
        when (event) {
            NewsListEvent.Refresh -> loadInitialArticles()
            NewsListEvent.LoadMore -> loadMoreArticles()
            is NewsListEvent.Search -> searchArticles(event.query)
            NewsListEvent.ClearSearch -> clearSearch()
            is NewsListEvent.ArticleClicked -> onArticleClicked(event.articleUI.id) // Pass only the ID
            NewsListEvent.BackClick -> onBackClicked()
        }
    }

    private fun loadInitialArticles() {
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = articlesComponent.loadTopHeadlines()
            result.fold(
                onSuccess = { success ->
                    _state.update {
                        it.copy(
                            articleUIS = success.articleUIS,
                            isLoading = false,
                            currentPage = success.currentPage,
                            totalPages = success.totalPages,
                            hasMore = success.currentPage < success.totalPages
                        )
                    }
                },
                onFailure = { failure ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = failure.message ?: "Failed to load articles"
                        )
                    }
                }
            )
        }
    }

    private fun loadMoreArticles() {
        if (_state.value.isLoading || !_state.value.hasMore) return

        coroutineScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val nextPage = _state.value.currentPage + 1
            val result = if (_state.value.searchQuery.isBlank()) {
                articlesComponent.loadTopHeadlines(page = nextPage)
            } else {
                articlesComponent.searchArticles(_state.value.searchQuery, page = nextPage)
            }

            result.fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            articleUIS = it.articleUIS + data.articleUIS,
                            isLoading = false,
                            currentPage = data.currentPage,
                            totalPages = data.totalPages,
                            hasMore = data.currentPage < data.totalPages
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to load more articles"
                        )
                    }
                }
            )
        }
    }

    private fun searchArticles(query: String) {
        _state.update { it.copy(searchQuery = query) }

        searchJob?.cancel()
        if (query.isBlank()) {
            clearSearch()
            return
        }

        searchJob = coroutineScope.launch {
            delay(500) // Debounce search
            _state.update { it.copy(isSearching = true, isLoading = true, error = null) }

            val result = articlesComponent.searchArticles(query)
            result.fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            articleUIS = data.articleUIS,
                            isLoading = false,
                            isSearching = false,
                            currentPage = data.currentPage,
                            totalPages = data.totalPages,
                            hasMore = data.currentPage < data.totalPages
                        )
                    }
                },
                onFailure = { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSearching = false,
                            error = exception.message ?: "Search failed"
                        )
                    }
                }
            )
        }
    }

    private fun clearSearch() {
        searchJob?.cancel()
        _state.update { it.copy(searchQuery = "", isSearching = false) }
        loadInitialArticles()
    }
}