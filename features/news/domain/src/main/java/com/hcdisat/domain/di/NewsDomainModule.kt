package com.hcdisat.domain.di

import com.hcdisat.domain.usecases.LoadNewsUseCase
import com.hcdisat.domain.usecases.LoadNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface NewsDomainModule {
    @Binds
    @ViewModelScoped
    fun bindsLoadNewsUseCase(impl: LoadNewsUseCaseImpl): LoadNewsUseCase
}
