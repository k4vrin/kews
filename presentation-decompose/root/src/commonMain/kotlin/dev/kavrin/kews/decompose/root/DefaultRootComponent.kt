package dev.kavrin.kews.decompose.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import dev.kavrin.kews.decompose.news.articles.DefaultArticlesComponent
import dev.kavrin.kews.decompose.news.news_details.DefaultArticleDetailComponent
import dev.kavrin.kews.decompose.news.news_list.DefaultNewsListComponent
import dev.kavrin.kews.decompose.news.news_list.NewsListComponent
import dev.kavrin.kews.decompose.welcome.DefaultWelcomeComponent
import dev.kavrin.kews.decompose.welcome.WelcomeComponent
import dev.kavrin.kews.domain.news.usecase.GetArticleByIdUseCase
import dev.kavrin.kews.domain.news.usecase.GetTopHeadlinesUseCase
import dev.kavrin.kews.domain.news.usecase.SearchArticlesUseCase
import dev.kavrin.kews.news.ArticleDetailComponent
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase by inject()
    private val searchArticlesUseCase: SearchArticlesUseCase by inject()
    private val getArticleByIdUseCase: GetArticleByIdUseCase by inject()

    // Create shared articles component
    private val articlesComponent = DefaultArticlesComponent(
        componentContext = componentContext,
        getTopHeadlinesUseCase = getTopHeadlinesUseCase,
        searchArticlesUseCase = searchArticlesUseCase,
        getArticleByIdUseCase = getArticleByIdUseCase
    )

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Welcome,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            is Config.Main -> RootComponent.Child.Main(
                mainComponent(componentContext)
            )

            is Config.Welcome -> RootComponent.Child.Welcome(
                welcomeComponent(componentContext)
            )

            is Config.NewsList -> RootComponent.Child.NewsList(
                newsListComponent(componentContext)
            )

            is Config.ArticleDetail -> RootComponent.Child.ArticleDetail(
                articleDetailComponent(componentContext, config.articleId)
            )
        }
    }

    private fun mainComponent(componentContext: ComponentContext): MainComponent {
        return DefaultMainComponent(
            componentContext = componentContext,
            onWelcomeClicked = {
                navigation.pushNew(Config.Welcome)
            },
            onNewsClicked = {
                navigation.pushNew(Config.NewsList)
            }
        )
    }

    private fun welcomeComponent(componentContext: ComponentContext): WelcomeComponent {
        return DefaultWelcomeComponent(
            componentContext = componentContext,
            onFinished = {
                navigation.pop()
            },
            onNavigateToNewsClicked = {
                navigation.pushNew(Config.NewsList)
            }
        )
    }

    private fun newsListComponent(componentContext: ComponentContext): NewsListComponent {
        return DefaultNewsListComponent(
            componentContext = componentContext,
            articlesComponent = articlesComponent,
            onArticleClicked = { articleId ->
                navigation.pushNew(Config.ArticleDetail(articleId))
            },
            onBackClicked = {
                navigation.pop()
            }
        )
    }

    private fun articleDetailComponent(
        componentContext: ComponentContext,
        articleId: String,
    ): ArticleDetailComponent {
        return DefaultArticleDetailComponent(
            componentContext = componentContext,
            articleId = articleId,
            articlesComponent = articlesComponent,
            onBackClicked = {
                navigation.pop()
            }
        )
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config

        @Serializable
        data object Welcome : Config

        @Serializable
        data object NewsList : Config

        @Serializable
        data class ArticleDetail(
            val articleId: String
        ) : Config
    }
}
