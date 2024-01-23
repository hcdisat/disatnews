package com.hcdisat.disatnews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcdisat.api.model.DataStoreSession
import com.hcdisat.common.di.IO
import com.hcdisat.disatnews.model.Destination
import com.hcdisat.disatnews.model.MainState
import com.hcdisat.disatnews.model.NavigationEvent
import com.hcdisat.domain.usecases.CompleteOnboardingUseCase
import com.hcdisat.domain.usecases.GetOnboardingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IO private val ioDispatcher: CoroutineDispatcher,
    private val getOnboarding: GetOnboardingStateUseCase,
    private val completeOnboarding: CompleteOnboardingUseCase
) : ViewModel() {
    var state by mutableStateOf(MainState())
        private set

    init {
        viewModelScope.launch {
            withContext(ioDispatcher) { getOnboarding() }.collect(::handleSession)
        }
    }

    fun receiveEvent(event: NavigationEvent) = when (event) {
        NavigationEvent.OnboardingCompleted -> completeOnboardingStep()
    }

    private fun handleSession(session: DataStoreSession) = when (session) {
        DataStoreSession.Completed -> state = state.copy(startingDestination = Destination.Tmp)
        DataStoreSession.Pending -> state = state.copy(startingDestination = Destination.Onboarding)
    }

    private fun completeOnboardingStep() {
        viewModelScope.launch {
            withContext(ioDispatcher) { completeOnboarding() }
            state = state.copy(startingDestination = Destination.Tmp)
        }
    }
}

