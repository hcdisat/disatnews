package com.hcdisat.disatnews.model

data class MainState(
    val startingDestination: Destination = Destination.Onboarding
)

sealed class Destination(val route: String) {
    data object Onboarding : Destination("onboarding")
    data object Tmp : Destination("tmp")
}