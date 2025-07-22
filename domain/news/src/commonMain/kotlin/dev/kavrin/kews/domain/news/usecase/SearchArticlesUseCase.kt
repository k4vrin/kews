package dev.kavrin.kews.domain.news.usecase

import dev.kavrin.kews.domain.news.model.ArticlesResponse
import dev.kavrin.kews.domain.news.repository.NewsRepository

class SearchArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 20,
        sortBy: String = "publishedAt"
    ): Result<ArticlesResponse> {
        return newsRepository.searchArticles(query, page, pageSize, sortBy)
    }
}
