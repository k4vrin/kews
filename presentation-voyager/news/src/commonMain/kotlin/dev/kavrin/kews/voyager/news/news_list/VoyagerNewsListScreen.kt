package dev.kavrin.kews.voyager.news.news_list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import dev.kavrin.kews.voyager.news.news_details.VoyagerNewsDetailsScreen
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VoyagerNewsListTab : Tab {

    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Newspaper)
            return remember {
                TabOptions(index = 0u, title = "News", icon = icon)
            }
        }

    @Composable
    override fun Content() {
        Navigator(VoyagerNewsListScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}

class VoyagerNewsListScreen : Screen {
    @OptIn(ExperimentalUuidApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Column {
            Text("News List")
            Button(onClick = {
                // Navigate two levels up—from the tab's inner Navigator, through the TabNavigator,
                // to the root-level Navigator—so that the details screen is pushed on the main navigation stack.
                // This ensures the tab bar is hidden on the details screen while preserving the tab state.
                navigator.parent?.parent?.push(VoyagerNewsDetailsScreen(Uuid.random().toString()))
            }) {
                Text("Go to News Details")
            }
        }
    }
}