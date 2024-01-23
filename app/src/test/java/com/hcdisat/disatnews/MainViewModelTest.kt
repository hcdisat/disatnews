package com.hcdisat.disatnews

import com.hcdisat.api.model.DataStoreSession
import com.hcdisat.disatnews.model.Destination
import com.hcdisat.disatnews.model.NavigationEvent
import com.hcdisat.domain.usecases.CompleteOnboardingUseCase
import com.hcdisat.domain.usecases.GetOnboardingStateUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private val scheduler = TestCoroutineScheduler()
    private val mainDispatcher = StandardTestDispatcher(scheduler)
    private val ioDispatcher = StandardTestDispatcher(scheduler)

    private val completeOnboarding: CompleteOnboardingUseCase = mockk()
    private val getOnboarding: GetOnboardingStateUseCase = mockk()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainDispatcher)

        every { getOnboarding.invoke() } returns flow { emit(DataStoreSession.Pending) }

        mainViewModel = MainViewModel(
            ioDispatcher = ioDispatcher,
            getOnboarding = getOnboarding,
            completeOnboarding = completeOnboarding
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `it should set the starting navigation to onboarding if the state is Pending`() {
        scheduler.advanceUntilIdle()

        val current = mainViewModel.state.startingDestination

        assertTrue(current == Destination.Onboarding)
        verify(exactly = 1) { getOnboarding.invoke() }
    }

    @Test
    fun `it should set the starting navigation to Tmp if the state is Completed`() {
        every { getOnboarding.invoke() } returns flow { emit(DataStoreSession.Completed) }
        scheduler.advanceUntilIdle()

        val current = mainViewModel.state.startingDestination

        assertEquals(Destination.Tmp, current)
        verify(exactly = 1) { getOnboarding.invoke() }
    }

    @Test
    fun `it should complete the onboarding process`() {
        coEvery { completeOnboarding.invoke() } just runs
        scheduler.advanceUntilIdle()

        mainViewModel.receiveEvent(NavigationEvent.OnboardingCompleted)

        assertEquals(Destination.Onboarding, mainViewModel.state.startingDestination)
        scheduler.advanceUntilIdle()

        assertEquals(Destination.Tmp, mainViewModel.state.startingDestination)
        coVerify(exactly = 1) { completeOnboarding.invoke() }
        assertEquals(Destination.Tmp, mainViewModel.state.startingDestination)
    }
}