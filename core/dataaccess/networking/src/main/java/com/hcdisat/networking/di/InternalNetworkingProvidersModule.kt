package com.hcdisat.networking.di

import com.google.gson.Gson
import com.hcdisat.networking.BuildConfig
import com.hcdisat.networking.interceptor.ApiKeyInterceptor
import com.hcdisat.networking.service.NetworkConstants.API_KEY
import com.hcdisat.networking.service.NetworkConstants.BASE_URL
import com.hcdisat.networking.service.NetworkConstants.BASE_URL_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class InternalNetworkingProvidersModule {
    @Provides
    @Named(API_KEY)
    fun providesApiKey(): String = BuildConfig.NEWS_API_KEY

    @Provides
    @Named(BASE_URL_KEY)
    fun providesBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(Duration.ofSeconds(10L))
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        gson: Gson,
        @Named(BASE_URL_KEY) baseUrl: String
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}