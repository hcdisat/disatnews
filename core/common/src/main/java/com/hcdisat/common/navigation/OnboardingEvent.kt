package com.hcdisat.common.navigation

sealed interface OnboardingEvent {
    data object Completed : OnboardingEvent
    data object Pending : OnboardingEvent
}