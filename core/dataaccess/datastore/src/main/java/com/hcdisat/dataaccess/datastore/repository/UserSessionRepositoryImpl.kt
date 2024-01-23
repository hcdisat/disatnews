package com.hcdisat.dataaccess.datastore.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.hcdisat.api.repository.UserSessionRepository
import com.hcdisat.common.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSessionRepositoryImpl @Inject constructor(
    private val datastore: DataStore<Preferences>
) : UserSessionRepository {
    private val keys = SessionKeys()

    override suspend fun saveAppEntry() {
        datastore.edit { preferences ->
            preferences[keys.getAppEntryKey()] = true
        }
    }

    override fun getAppEntry(): Flow<Boolean> = datastore.data
        .catch {
            Log.e("UserSessionImpl", "getAppEntry: ${it.localizedMessage}")
            emptyPreferences()
        }
        .map { preferences ->
            preferences[keys.getAppEntryKey()] ?: false
        }
}

class SessionKeys {
    fun getAppEntryKey(): Preferences.Key<Boolean> =
        booleanPreferencesKey(AppSettings.APP_ENTRY_KEY)
}