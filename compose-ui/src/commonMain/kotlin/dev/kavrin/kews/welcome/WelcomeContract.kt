package dev.kavrin.kews.welcome

// --- UI contract for Welcome ---
data class WelcomeUiState(
    val greetingText: String = "Welcome from Decompose!",
)

sealed interface WelcomeUiEvent {
    object BackClicked : WelcomeUiEvent
    object GreetingClicked : WelcomeUiEvent
    object NewsClicked : WelcomeUiEvent
}