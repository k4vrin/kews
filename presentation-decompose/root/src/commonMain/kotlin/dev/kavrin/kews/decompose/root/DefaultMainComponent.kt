package dev.kavrin.kews.decompose.root

import com.arkivanov.decompose.ComponentContext

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val onWelcomeClicked: () -> Unit,
    private val onNewsClicked: () -> Unit,
) : MainComponent, ComponentContext by componentContext {

    override fun onShowWelcomeClicked() {
        onWelcomeClicked()
    }

    override fun onShowNewsClicked() {
        onNewsClicked()
    }
}
