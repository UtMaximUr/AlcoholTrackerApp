package com.utmaximur.app.android

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.handleDeepLink
import com.utmaximur.root.RootComponent
import com.utmaximur.root.ui.RootScreen
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

internal class MainActivity : ComponentActivity(), KoinComponent {

    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
            ),
        )
        val root = get<RootComponent> {
            parametersOf(defaultComponentContext())
        }
        handleDeepLink { uri ->
            root.handleDeepLink(uri?.host)
        }
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                content = { RootScreen(root) },
            )
        }
    }
}
