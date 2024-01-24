package com.hcdisat.networking.service

import com.hcdisat.networking.model.EverythingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EveryNewsService {
    @GET("everything")
    suspend fun getAll(@Query("q") query: String): Response<EverythingResponse>
}