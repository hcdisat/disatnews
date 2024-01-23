package com.hcdisat.disatnews.di

import com.hcdisat.disatnews.navigation.Router
import com.hcdisat.disatnews.navigation.RouterImpl
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
    fun bindsRouter(impl: RouterImpl): Router
}