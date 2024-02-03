package com.hcdisat.onboarding.di

import com.hcdisat.onboarding.data.repository.UserSessionRepositoryImpl
import com.hcdisat.onboarding.domain.repository.UserSessionRepository
import com.hcdisat.onboarding.domain.usecases.CompleteOnboardingUseCase
import com.hcdisat.onboarding.domain.usecases.CompleteOnboardingUseCaseImpl
import com.hcdisat.onboarding.domain.usecases.GetOnboardingStateUseCase
import com.hcdisat.onboarding.domain.usecases.GetOnboardingStateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface OnboardingModule {
    @Binds
    @ViewModelScoped
    fun bindsGetOnboardingStateUseCase(impl: GetOnboardingStateUseCaseImpl): GetOnboardingStateUseCase

    @Binds
    @ViewModelScoped
    fun bindsCompleteOnboardingUseCase(impl: CompleteOnboardingUseCaseImpl): CompleteOnboardingUseCase

    @Binds
    @ViewModelScoped
    fun bindsUserSessionRepository(impl: UserSessionRepositoryImpl): UserSessionRepository
}