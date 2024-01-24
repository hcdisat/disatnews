package com.hcdisat.disatnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.hcdisat.disatnews.navigation.Router
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !router.isReady
            }
        }
        enableEdgeToEdge()
        setContent {
            DisatNewsTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainActivity.MainScreen() {
    WithEdgeToEdge(isLightMode = !isSystemInDarkTheme())

    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
        val navHostController = rememberNavController()
        router.SetupNavGraph(
            startDestination = "onboarding",
            navHostController = navHostController
        )
    }
}

@Composable
fun MainActivity.WithEdgeToEdge(isLightMode: Boolean) {
    LaunchedEffect(key1 = isLightMode) {
        val color = Color.Transparent.toArgb()

        val style = if (isLightMode)
            SystemBarStyle.light(scrim = color, darkScrim = color)
        else SystemBarStyle.dark(scrim = color)

        enableEdgeToEdge(statusBarStyle = style, navigationBarStyle = style)
    }
}