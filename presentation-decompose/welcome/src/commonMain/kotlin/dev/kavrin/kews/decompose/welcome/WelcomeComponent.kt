package dev.kavrin.kews.decompose.welcome

import com.arkivanov.decompose.value.Value

interface WelcomeComponent {

    val model: Value<Model>

    fun onUpdateGreetingText()
    fun onBackClicked()
    fun onNewsClicked()

    data class Model(
        val greetingText: String = "Welcome from Decompose!",
    )
}
