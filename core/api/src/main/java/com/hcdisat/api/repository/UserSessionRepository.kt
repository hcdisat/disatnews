package com.hcdisat.api.repository

import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {
    suspend fun saveAppEntry()
    fun getAppEntry(): Flow<Boolean>
}