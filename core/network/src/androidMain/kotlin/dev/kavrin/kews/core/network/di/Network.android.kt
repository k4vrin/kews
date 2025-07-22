package dev.kavrin.kews.core.network.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkPlatformModule: Module = module {
    single { provideHttpClientEngine() }
}

private fun provideHttpClientEngine(): HttpClientEngine = OkHttp.create()
