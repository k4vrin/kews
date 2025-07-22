package dev.kavrin.kews.decompose.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.kavrin.kews.decompose.welcome.WelcomeComponent
import dev.kavrin.kews.news.ArticleDetailComponent
import dev.kavrin.kews.decompose.news.news_list.NewsListComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Main(val component: MainComponent) : Child()
        class Welcome(val component: WelcomeComponent) : Child()
        class NewsList(val component: NewsListComponent) : Child()
        class ArticleDetail(val component: ArticleDetailComponent) : Child()
    }
}