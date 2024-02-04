package com.hcdisat.disatnews.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hcdisat.common.navigation.OnboardingEvent
import com.hcdisat.onboarding.OnboardingRoute
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface Router {
    val isReady: Boolean
    @Composable
    fun SetupNavGraph(startDestination: String, navHostController: NavHostController)
}

class RouterImpl @Inject constructor(
    private val onboardingRoute: OnboardingRoute,
    @ApplicationContext private val context: Context
) : Router {

    private var _isReady by mutableStateOf(false)

    override val isReady: Boolean get() = _isReady

    @Composable
    override fun SetupNavGraph(startDestination: String, navHostController: NavHostController) {
        NavHost(navController = navHostController, startDestination = startDestination) {
            registerOnboarding()
        }
    }

    private fun NavGraphBuilder.registerOnboarding() {
        onboardingRoute.register(this) { event ->
            _isReady = when (event) {
                OnboardingEvent.Completed -> {
                    Toast.makeText(context, "Onboarding completed", Toast.LENGTH_SHORT).show()
                    true
                }

                OnboardingEvent.Pending -> true
            }
        }
    }
}