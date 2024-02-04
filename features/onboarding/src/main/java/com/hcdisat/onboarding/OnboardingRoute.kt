package com.hcdisat.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import com.hcdisat.common.navigation.OnboardingEvent
import com.hcdisat.common.navigation.Route
import com.hcdisat.onboarding.model.OnboardingState
import com.hcdisat.onboarding.screen.OnboardingScreen
import com.hcdisat.onboarding.screen.OnboardingViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

interface OnboardingRoute : Route<OnboardingEvent>

class OnboardingRouteImpl @Inject constructor() : OnboardingRoute {
    override val route: String get() = "onboarding"

    override fun register(
        builder: androidx.navigation.NavGraphBuilder,
        onEvent: (OnboardingEvent) -> Unit
    ) {
        builder.composable(route = route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            when (viewModel.state) {
                OnboardingState.OnboardingCompleted -> {
                    Delay()
                    onEvent(OnboardingEvent.Completed)
                }

                OnboardingState.OnboardingPending -> {
                    Delay()
                    onEvent(OnboardingEvent.Pending)
                    OnboardingScreen(sendAction = viewModel::receiveEvent)
                }

                OnboardingState.OnboardingPreparing -> Unit
            }
        }
    }

    @Composable
    private fun Delay() {
        LaunchedEffect(key1 = Unit) { delay(1000L) }
    }
}