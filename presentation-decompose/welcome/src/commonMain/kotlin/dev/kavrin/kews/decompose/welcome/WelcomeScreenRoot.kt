package dev.kavrin.kews.decompose.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.kavrin.kews.welcome.WelcomeContent
import dev.kavrin.kews.welcome.WelcomeUiEvent
import dev.kavrin.kews.welcome.WelcomeUiState

@Composable
fun WelcomeScreenRoot(
    component: WelcomeComponent,
    modifier: Modifier = Modifier,
) {
    val model by component.model.subscribeAsState()
    WelcomeContent(
        state = WelcomeUiState(greetingText = model.greetingText),
        onEvent = {
            when (it) {
                WelcomeUiEvent.BackClicked -> component.onBackClicked()
                WelcomeUiEvent.GreetingClicked -> component.onUpdateGreetingText()
                WelcomeUiEvent.NewsClicked -> component.onNewsClicked()
            }
        },
        modifier = modifier,
    )
}
