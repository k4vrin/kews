package dev.kavrin.kews.voyager.news.di

import dev.kavrin.kews.voyager.news.news_list.VoyagerNewsListViewModel
import org.koin.dsl.module

val newsPresModule = module {
    factory {
        VoyagerNewsListViewModel(
            getTopHeadlinesUseCase = get(),
            searchArticlesUseCase = get(),
            onArticleClicked = get()
        )
    }
}