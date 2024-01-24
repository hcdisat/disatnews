package com.hcdisat.networking.interceptor

import com.hcdisat.networking.service.NetworkConstants.API_KEY
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor(
    @Named(API_KEY) private val apiKey: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val urlBuilder = originalRequest.url.newBuilder().addApiKey()

        val finalRequest = originalRequest.newBuilder()
        finalRequest.url(urlBuilder.build())

        return chain.proceed(finalRequest.build())
    }

    private fun HttpUrl.Builder.addApiKey(): HttpUrl.Builder {
        return addQueryParameter(API_KEY, apiKey)
    }
}