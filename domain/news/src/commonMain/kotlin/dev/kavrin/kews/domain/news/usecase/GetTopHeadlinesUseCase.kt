package dev.kavrin.kews.domain.news.usecase

import dev.kavrin.kews.domain.news.model.ArticlesResponse
import dev.kavrin.kews.domain.news.repository.NewsRepository

class GetTopHeadlinesUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(
        country: String = "us",
        category: String = "general",
        page: Int = 1,
        pageSize: Int = 20
    ): Result<ArticlesResponse> {
        return newsRepository.getTopHeadlines(country, category, page, pageSize)
    }
}
