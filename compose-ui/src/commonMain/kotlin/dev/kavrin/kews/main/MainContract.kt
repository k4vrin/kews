package dev.kavrin.kews.main

// --- UI contract for Main ---
data class MainUiState(
    val someState: String = "News Showcase",
)

sealed interface MainUiEvent {
    object ShowNewsClicked : MainUiEvent
    object ShowWelcomeClicked : MainUiEvent
}
