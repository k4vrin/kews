package dev.kavrin.kews.data.news.di

import dev.kavrin.kews.data.news.remote.api.NewsApiService
import dev.kavrin.kews.domain.news.repository.NewsRepository
import dev.kavrin.kews.data.news.repository.NewsRepositoryImpl
import dev.kavrin.kews.domain.news.usecase.GetArticleByIdUseCase
import dev.kavrin.kews.domain.news.usecase.GetTopHeadlinesUseCase
import dev.kavrin.kews.domain.news.usecase.SearchArticlesUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val newsModule = module {
    // API Service - inject both HttpClient and API key
    single { NewsApiService(get(), get<String>(named("NEWS_API_KEY"))) }

    // Repository
    single<NewsRepository> { NewsRepositoryImpl(get()) }

    // Use Cases
    single { GetTopHeadlinesUseCase(get()) }
    single { SearchArticlesUseCase(get()) }
    single { GetArticleByIdUseCase(get()) }
}