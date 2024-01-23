package com.hcdisat.disatnews

import com.hcdisat.api.model.DataStoreSession
import com.hcdisat.disatnews.model.Destination
import com.hcdisat.domain.usecases.CompleteOnboardingUseCase
import com.hcdisat.domain.usecases.GetOnboardingStateUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
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
        mainViewModel = MainViewModel(
            ioDispatcher = ioDispatcher,
            getOnboarding = getOnboarding
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `it should set the starting navigation to onboarding if the state is Pending`() {
        every { getOnboarding.invoke() } returns flow { emit(DataStoreSession.Pending) }
        scheduler.runCurrent()

        val current = mainViewModel.state.startingDestination

        assertTrue(current == Destination.Onboarding)
        verify(exactly = 1) { getOnboarding.invoke() }
    }

    @Test
    fun `it should set the starting navigation to Tmp if the state is Completed`() {
        every { getOnboarding.invoke() } returns flow { emit(DataStoreSession.Completed) }
        scheduler.runCurrent()

        val current = mainViewModel.state.startingDestination

        assertEquals(Destination.Tmp, current)
        verify(exactly = 1) { getOnboarding.invoke() }
    }
}