package dev.kavrin.kews.decompose.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kavrin.kews.main.MainContent
import dev.kavrin.kews.main.MainUiEvent
import dev.kavrin.kews.main.MainUiState

@Composable
fun MainScreenRoot(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    // If actual state exists in component, subscribe here. For static UI, just pass default state.
    MainContent(
        state = MainUiState(),
        onEvent = {
            when (it) {
                MainUiEvent.ShowNewsClicked -> component.onShowNewsClicked()
                MainUiEvent.ShowWelcomeClicked -> component.onShowWelcomeClicked()
            }
        },
        modifier = modifier,
    )
}
