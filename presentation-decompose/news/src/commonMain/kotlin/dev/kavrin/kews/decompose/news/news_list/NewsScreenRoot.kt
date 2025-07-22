package dev.kavrin.kews.decompose.news.news_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.kavrin.kews.news.NewsListScreen

@Composable
fun NewsListScreenRoot(
    component: NewsListComponent,
    modifier: Modifier = Modifier,
) {

    val state by component.state.subscribeAsState()

    NewsListScreen(
        state = state,
        onEvent = { component.onEvent(it) },
        modifier = modifier,
    )

}