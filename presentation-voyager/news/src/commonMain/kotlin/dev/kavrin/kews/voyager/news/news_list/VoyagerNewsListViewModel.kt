package dev.kavrin.kews.voyager.news.news_list

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.news.NewsListEvent
import dev.kavrin.kews.news.NewsListState
import dev.kavrin.kews.presentation.mappers.toDomain
import dev.kavrin.kews.presentation.mappers.toUI
import dev.kavrin.kews.domain.news.usecase.GetTopHeadlinesUseCase
import dev.kavrin.kews.domain.news.usecase.SearchArticlesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VoyagerNewsListViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val onArticleClicked: (Article) -> Unit,
) : ScreenModel {

    private val _state = MutableStateFlow(NewsListState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadInitialArticles()
    }

    fun onEvent(event: NewsListEvent) {
        when (event) {
            NewsListEvent.Refresh -> loadInitialArticles()
            NewsListEvent.LoadMore -> loadMoreArticles()
            is NewsListEvent.Search -> searchArticles(event.query)
            NewsListEvent.ClearSearch -> clearSearch()
            is NewsListEvent.ArticleClicked -> onArticleClicked(event.articleUI.toDomain())
            NewsListEvent.BackClick -> TODO()
        }
    }

    private fun loadInitialArticles() {
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getTopHeadlinesUseCase()
            result.fold(
                onSuccess = { success ->
                    _state.update {
                        it.copy(
                            articleUIS = success.articles.map { article -> article.toUI() },
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

        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val nextPage = _state.value.currentPage + 1
            val result = if (_state.value.searchQuery.isBlank()) {
                getTopHeadlinesUseCase(page = nextPage)
            } else {
                searchArticlesUseCase(_state.value.searchQuery, page = nextPage)
            }

            result.fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            articleUIS = it.articleUIS + data.articles.map { article -> article.toUI() },
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

        searchJob = screenModelScope.launch {
            delay(500) // Debounce search
            _state.update { it.copy(isSearching = true, isLoading = true, error = null) }

            val result = searchArticlesUseCase(query)
            result.fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            articleUIS = data.articles.map { article -> article.toUI() },
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