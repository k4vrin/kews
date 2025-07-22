package dev.kavrin.kews.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeContent(
    state: WelcomeUiState,
    onEvent: (WelcomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Welcome") },
//                navigationIcon = {
//                    IconButton(onClick = { onEvent(WelcomeUiEvent.BackClicked) }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.greetingText,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Button(
                onClick = { onEvent(WelcomeUiEvent.NewsClicked) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Go to News")
            }
        }
    }
}

@Preview
@Composable
fun WelcomeContentPreview() {
    WelcomeContent(state = WelcomeUiState(), onEvent = {})
}