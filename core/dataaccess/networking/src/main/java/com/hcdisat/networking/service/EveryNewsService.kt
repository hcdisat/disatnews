package com.hcdisat.networking.service

import com.hcdisat.networking.model.EverythingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EveryNewsService {
    @GET("everything")
    suspend fun search(
        @Query("q") query: String,
        @Query("sources") sources: String,
        @Query("page") page: Int,
//        @Query("pageSize") pageSize: String,
    ): Response<EverythingResponse>

    @GET("everything")
    suspend fun getBySources(
        @Query("sources") sources: String,
        @Query("page") page: Int,
//        @Query("pageSize") pageSize: String,
    ): Response<EverythingResponse>
}