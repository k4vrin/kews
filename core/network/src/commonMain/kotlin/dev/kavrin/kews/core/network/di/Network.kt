package dev.kavrin.kews.core.network.di

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val networkPlatformModule: Module

val networkCommonModule = module {
    single { provideHttpClient(engine = get()) }
}

fun provideHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        // Install Content Negotiation with kotlinx.serialization Json support.
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        // Install Logging for debugging purposes.
        install(Logging) {
            logger = KermitKtorLogger(Logger.withTag("Ktor"))
            level = LogLevel.ALL
        }
        // Install HttpTimeout plugin with sample timeout values.
        install(HttpTimeout) {
            requestTimeoutMillis = 60000
            connectTimeoutMillis = 60000
            socketTimeoutMillis = 60000
        }
    }
}

class KermitKtorLogger(private val kermit: Logger) : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        // You can choose the log level (i, d, etc.) based on your needs.
        kermit.i { message }
    }
}