package com.hcdisat.onboarding

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import com.hcdisat.api.navigation.Route
import com.hcdisat.onboarding.model.OnboardingState
import com.hcdisat.onboarding.screen.OnboardingScreen
import com.hcdisat.onboarding.screen.OnboardingViewModel
import javax.inject.Inject

interface OnboardingRoute : Route<Unit>

class OnboardingRouteImpl @Inject constructor() : OnboardingRoute {
    override val route: String get() = "onboarding"

    override fun register(
        builder: androidx.navigation.NavGraphBuilder,
        onEvent: (Unit) -> Unit
    ) {
        builder.composable(route = route) {
            val viewModel: OnboardingViewModel = hiltViewModel()
            when (viewModel.state) {
                OnboardingState.OnboardingCompleted -> onEvent(Unit)
                OnboardingState.OnboardingPending ->
                    OnboardingScreen(sendAction = viewModel::receiveEvent)
            }
        }
    }
}