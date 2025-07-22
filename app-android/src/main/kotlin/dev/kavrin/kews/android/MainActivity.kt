package dev.kavrin.kews.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import dev.kavrin.kews.decompose.root.DefaultRootComponent
import dev.kavrin.kews.decompose.root.RootContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext()
        )

        setContent {
            RootContent(component = root)
//            VoyagerRootContent()
        }
    }
}
