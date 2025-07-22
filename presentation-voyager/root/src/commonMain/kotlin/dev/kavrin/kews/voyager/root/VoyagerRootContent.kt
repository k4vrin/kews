package dev.kavrin.kews.voyager.root

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dev.kavrin.kews.voyager.home.VoyagerMainScreen

@Composable
fun VoyagerRootContent() {
    Navigator(VoyagerMainScreen()) { navigator ->
        Scaffold(
            content = {
                CurrentScreen()
            }
        )
    }
}