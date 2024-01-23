package com.hcdisat.domain.usecases

import com.hcdisat.api.repository.UserSessionRepository
import javax.inject.Inject

interface CompleteOnboardingUseCase {
    suspend operator fun invoke()
}

class CompleteOnboardingUseCaseImpl @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : CompleteOnboardingUseCase {
    override suspend fun invoke() = userSessionRepository.saveAppEntry()
}

