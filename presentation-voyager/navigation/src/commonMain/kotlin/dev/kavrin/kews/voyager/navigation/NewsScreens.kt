package dev.kavrin.kews.voyager.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class NewsScreen : ScreenProvider {
    object NewsList : NewsScreen()
    data class NewsDetails(val id: String) : NewsScreen()
}
