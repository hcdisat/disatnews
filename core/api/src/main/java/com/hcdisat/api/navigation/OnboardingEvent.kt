package com.hcdisat.api.navigation

sealed interface OnboardingEvent {
    data object Completed : OnboardingEvent
    data object Pending : OnboardingEvent
}