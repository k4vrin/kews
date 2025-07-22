package dev.kavrin.kews.voyager.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.kavrin.kews.voyager.rss.VoyagerRssTab
import dev.kavrin.kews.voyager.news.news_list.VoyagerNewsListTab

class VoyagerMainScreen: Screen {
    @Composable
    override fun Content() {

        TabNavigator(VoyagerNewsListTab()) {

            Scaffold(
                content = { CurrentTab() },
                bottomBar = {
                    NavigationBar {
                        TabNavigationItem(VoyagerNewsListTab())
                        TabNavigationItem(VoyagerRssTab())
                    }
                }
            )
        }

    }

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { tab.options.icon?.let { Icon(painter = it, contentDescription = tab.options.title) } }
    )
}