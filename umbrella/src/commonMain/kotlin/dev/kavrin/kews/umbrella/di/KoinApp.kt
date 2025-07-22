package dev.kavrin.kews.umbrella.di

import dev.kavrin.kews.BuildKonfig
import dev.kavrin.kews.core.network.di.networkCommonModule
import dev.kavrin.kews.core.network.di.networkPlatformModule
import dev.kavrin.kews.data.news.di.newsModule
import dev.kavrin.kews.util.di.utilModule
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            networkCommonModule,
            networkPlatformModule,
            newsModule,
            utilModule,
            module {
                single(qualifier = named("NEWS_API_KEY")) { BuildKonfig.NEWS_API_KEY }
            }
        )
    }
}

fun initKoin() = initKoin {}