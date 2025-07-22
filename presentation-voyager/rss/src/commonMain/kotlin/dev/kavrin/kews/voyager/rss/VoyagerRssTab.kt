package dev.kavrin.kews.voyager.rss

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class VoyagerRssTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.RssFeed)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "RSS",
                    icon = icon
                )
            }
        }
    @Composable
    override fun Content() {
        Text("Rss Screen")
    }

}