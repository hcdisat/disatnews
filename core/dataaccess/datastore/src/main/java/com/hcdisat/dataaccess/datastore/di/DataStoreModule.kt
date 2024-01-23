package com.hcdisat.dataaccess.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.hcdisat.api.repository.UserSessionRepository
import com.hcdisat.common.AppSettings
import com.hcdisat.dataaccess.datastore.repository.UserSessionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

internal val Context.datastore: DataStore<Preferences>
        by preferencesDataStore(AppSettings.DATASTORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
class DatastorePreferencesProviderModule {
    @Provides
    @Singleton
    fun providesDatastore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.datastore
}

@Module
@InstallIn(SingletonComponent::class)
interface DatastorePreferencesModule {
    @Binds
    @Singleton
    fun bindsUserSessionRepository(impl: UserSessionRepositoryImpl): UserSessionRepository
}