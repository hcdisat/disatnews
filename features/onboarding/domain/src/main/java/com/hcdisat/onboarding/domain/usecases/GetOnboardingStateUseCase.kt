package com.hcdisat.onboarding.domain.usecases

import com.hcdisat.onboarding.domain.entity.OnboardingSession
import com.hcdisat.onboarding.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetOnboardingStateUseCase {
    operator fun invoke(): Flow<OnboardingSession>
}

class GetOnboardingStateUseCaseImpl @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : GetOnboardingStateUseCase {
    override fun invoke(): Flow<OnboardingSession> =
        userSessionRepository.getAppEntry()
            .map { isDone -> if (isDone) OnboardingSession.Completed else OnboardingSession.Pending }
}