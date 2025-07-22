package dev.kavrin.kews.umbrella

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import dev.kavrin.kews.decompose.root.RootComponent
import dev.kavrin.kews.decompose.root.RootContent
import platform.UIKit.UIViewController

fun rootViewController(root: RootComponent): UIViewController =
    ComposeUIViewController(
        content = {
            RootContent(component = root, modifier = Modifier.fillMaxSize())
        }
    )
