package com.hcdisat.onboarding.data.di

import com.hcdisat.onboarding.data.repository.UserSessionRepositoryImpl
import com.hcdisat.onboarding.domain.repository.UserSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface OnboardingDataModule {
    @Binds
    @Singleton
    fun bindsUserSessionRepository(impl: UserSessionRepositoryImpl): UserSessionRepository
}