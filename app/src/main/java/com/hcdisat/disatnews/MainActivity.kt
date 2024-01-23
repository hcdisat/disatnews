package com.hcdisat.disatnews

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.hcdisat.disatnews.model.Destination
import com.hcdisat.disatnews.model.NavigationEvent
import com.hcdisat.onboarding.OnboardingScreen
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DisatNewsTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    when (mainViewModel.state.startingDestination) {
                        Destination.Onboarding -> OnboardingScreen {
                            mainViewModel.receiveEvent(NavigationEvent.OnboardingCompleted)
                        }

                        Destination.Tmp -> localToast()
                    }
                }
            }
        }
    }

    private fun localToast() {
        Toast.makeText(this@MainActivity, "Onboarding completed", Toast.LENGTH_SHORT).show()
    }
}