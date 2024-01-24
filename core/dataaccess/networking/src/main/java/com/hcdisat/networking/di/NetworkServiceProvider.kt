package com.hcdisat.networking.di

import com.hcdisat.networking.service.EveryNewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceProvider {
    @Provides
    @Singleton
    fun providesEveryNewsService(
        retrofit: Retrofit.Builder,
        client: OkHttpClient
    ): EveryNewsService =
        retrofit
            .client(client)
            .build()
            .create(EveryNewsService::class.java)
}