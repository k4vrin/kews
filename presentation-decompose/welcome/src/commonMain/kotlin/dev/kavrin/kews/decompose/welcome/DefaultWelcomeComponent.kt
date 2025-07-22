package dev.kavrin.kews.decompose.welcome

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import dev.kavrin.kews.util.getPlatformName

class DefaultWelcomeComponent(
    private val componentContext: ComponentContext,
    private val onFinished: () -> Unit,
    private val onNavigateToNewsClicked: () -> Unit
) : WelcomeComponent, ComponentContext by componentContext {

    private val state = MutableValue(WelcomeComponent.Model())
    override val model: Value<WelcomeComponent.Model> = state

    init {
        onUpdateGreetingText()
    }

    override fun onUpdateGreetingText() {
        state.update { it.copy(greetingText = "Welcome from ${getPlatformName()}") }
    }

    override fun onBackClicked() {
        onFinished()
    }

    override fun onNewsClicked() {
        onNavigateToNewsClicked()
    }
}
