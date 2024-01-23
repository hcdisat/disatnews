package com.hcdisat.onboarding.model

import androidx.annotation.DrawableRes
import com.hcdisat.onboarding.R

internal data class Page(
    val title: String,
    val description: String,
    @DrawableRes val stepImage: Int
)

internal data class NavigatorLabels(
    val backText: String = "",
    val nextText: String = ""
)

internal val pages = listOf(
    Page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        stepImage = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        stepImage = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem Ipsum is simply dummy",
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        stepImage = R.drawable.onboarding3
    )
)

internal sealed interface OnboardingState {
    data object OnboardingCompleted : OnboardingState
    data object OnboardingPending : OnboardingState
}

internal sealed interface OnboardingAction {
    data object CompleteOnboarding : OnboardingAction
}