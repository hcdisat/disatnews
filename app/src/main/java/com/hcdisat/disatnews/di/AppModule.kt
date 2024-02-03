package com.hcdisat.disatnews.di

import com.hcdisat.disatnews.navigation.Router
import com.hcdisat.disatnews.navigation.RouterImpl
import com.hcdisat.onboarding.OnboardingRoute
import com.hcdisat.onboarding.OnboardingRouteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun bindsOnboardingRoute(impl: OnboardingRouteImpl): OnboardingRoute

    @Binds
    @Singleton
    fun bindsRouter(impl: RouterImpl): Router
}