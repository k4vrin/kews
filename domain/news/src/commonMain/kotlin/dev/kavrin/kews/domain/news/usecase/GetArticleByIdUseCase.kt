package dev.kavrin.kews.domain.news.usecase

import dev.kavrin.kews.domain.news.model.Article
import dev.kavrin.kews.domain.news.repository.NewsRepository

class GetArticleByIdUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(id: String): Result<Article> {
        return newsRepository.getArticleById(id)
    }
}
