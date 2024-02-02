package com.hcdisat.onboarding.data.repository

import com.hcdisat.dataaccess.datastore.repository.DataStoreService
import com.hcdisat.onboarding.domain.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val dataStoreService: DataStoreService
) : UserSessionRepository {
    override suspend fun saveAppEntry() = dataStoreService.saveAppEntry()

    override fun getAppEntry(): Flow<Boolean> = dataStoreService.getAppEntry()
}