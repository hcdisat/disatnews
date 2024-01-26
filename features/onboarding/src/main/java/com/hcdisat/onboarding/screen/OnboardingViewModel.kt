package com.hcdisat.onboarding.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.api.model.OnboardingSession
import com.hcdisat.common.di.IO
import com.hcdisat.domain.usecases.GetOnboardingStateUseCase
import com.hcdisat.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.hcdisat.onboarding.model.OnboardingAction
import com.hcdisat.onboarding.model.OnboardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    @IO private val ioDispatcher: CoroutineDispatcher,
    private val getOnboarding: GetOnboardingStateUseCase,
    private val completeOnboarding: CompleteOnboardingUseCase
) : ViewModel() {
    var state: OnboardingState by mutableStateOf(OnboardingState.OnboardingPreparing)
        private set

    init {
        viewModelScope.launch {
            withContext(ioDispatcher) { getOnboarding() }.collect(::handleSession)
        }
    }

    fun receiveEvent(event: OnboardingAction) = when (event) {
        OnboardingAction.CompleteOnboarding -> completeOnboardingStep()
    }

    private fun handleSession(session: OnboardingSession) = when (session) {
        OnboardingSession.Completed -> state = OnboardingState.OnboardingCompleted
        OnboardingSession.Pending -> state = OnboardingState.OnboardingPending
    }

    private fun completeOnboardingStep() {
        viewModelScope.launch {
            withContext(ioDispatcher) { completeOnboarding() }
            state = OnboardingState.OnboardingCompleted
        }
    }
}

