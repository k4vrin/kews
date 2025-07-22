package dev.kavrin.kews.voyager.news.news_details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

data class VoyagerNewsDetailsScreen(
    val id: String
): Screen {
    @Composable
    override fun Content() {
        Text(
            text = "News Details for ID: $id",
            modifier = Modifier.Companion
                .padding(16.dp)
                .fillMaxSize()
        )
    }
}