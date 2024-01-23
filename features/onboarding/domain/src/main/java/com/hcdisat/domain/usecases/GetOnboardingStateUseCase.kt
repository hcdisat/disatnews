package com.hcdisat.onboarding.domain.usecases

import com.hcdisat.api.model.DataStoreSession
import com.hcdisat.api.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetOnboardingStateUseCase {
    operator fun invoke(): Flow<DataStoreSession>
}

class GetOnboardingStateUseCaseImpl @Inject constructor(
    private val userSessionRepository: UserSessionRepository
) : GetOnboardingStateUseCase {
    override fun invoke(): Flow<DataStoreSession> =
        userSessionRepository.getAppEntry()
            .map { isDone -> if (isDone) DataStoreSession.Completed else DataStoreSession.Pending }
}