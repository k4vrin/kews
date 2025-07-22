package dev.kavrin.kews.decompose.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.kavrin.kews.decompose.welcome.WelcomeScreenRoot
import dev.kavrin.kews.decompose.news.news_details.ArticleDetailScreenRoot
import dev.kavrin.kews.decompose.news.news_list.NewsListScreenRoot

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val childStack by component.stack.subscribeAsState()

    Children(
        stack = childStack,
        modifier = modifier
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Main -> {
                MainScreenRoot(
                    component = instance.component,
                )
            }

            is RootComponent.Child.Welcome -> {
                WelcomeScreenRoot(
                    component = instance.component,
                )
            }

            is RootComponent.Child.NewsList -> {
                NewsListScreenRoot(
                    component = instance.component
                )
            }

            is RootComponent.Child.ArticleDetail -> {
                ArticleDetailScreenRoot(
                    component = instance.component
                )
            }
        }
    }
}
