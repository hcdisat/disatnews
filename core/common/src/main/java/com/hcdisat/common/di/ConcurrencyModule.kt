package com.hcdisat.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Default

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Main

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @Provides
    @IO
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Main
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Default
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}