package dev.kavrin.kews.util.di

import dev.kavrin.kews.util.AndroidUrlOpener
import dev.kavrin.kews.util.UrlOpener
import org.koin.core.module.Module
import org.koin.dsl.module

actual val utilModule: Module = module {
    factory<UrlOpener> { AndroidUrlOpener(get()) }
}