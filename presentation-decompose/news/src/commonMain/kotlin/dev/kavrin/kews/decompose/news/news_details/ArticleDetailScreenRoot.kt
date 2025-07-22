package dev.kavrin.kews.decompose.news.news_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.kavrin.kews.news.ArticleDetailComponent
import dev.kavrin.kews.news.ArticleDetailScreen

@Composable
fun ArticleDetailScreenRoot(
    component: ArticleDetailComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()
    ArticleDetailScreen(
        state = state,
        onEvent = component::onEvent,
        modifier = modifier
    )
}