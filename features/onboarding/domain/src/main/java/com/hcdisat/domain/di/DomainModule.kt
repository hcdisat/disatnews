package com.hcdisat.domain.di

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
interface DomainModule {
    @Binds
    @ViewModelScoped
    fun bindsGetOnboardingStateUseCase(impl: GetOnboardingStateUseCaseImpl): GetOnboardingStateUseCase

    @Binds
    @ViewModelScoped
    fun bindsCompleteOnboardingUseCase(impl: CompleteOnboardingUseCaseImpl): CompleteOnboardingUseCase
}