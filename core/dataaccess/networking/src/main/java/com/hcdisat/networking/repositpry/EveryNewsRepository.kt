package com.hcdisat.networking.repositpry

import com.hcdisat.networking.model.EverythingResponse
import com.hcdisat.networking.service.EveryNewsService
import javax.inject.Inject

interface EveryNewsRepository {
    suspend fun getBySources(
        vararg sources: String,
        page: Int,
        pageSize: Int
    ): Result<EverythingResponse>
}

class EveryNewsRepositoryImpl @Inject constructor(
    private val newsService: EveryNewsService
) : EveryNewsRepository {
    override suspend fun getBySources(
        vararg sources: String,
        page: Int,
        pageSize: Int
    ): Result<EverythingResponse> = runCatching {
        val response = newsService.getBySources(
            sources = sources.joinToString(separator = ","),
            page = page,
            pageSize = pageSize
        )

        val news = response.body()
        if (response.isSuccessful && news != null) {
            news
        } else {
            throw RuntimeException("every news response was empty or unsuccessful")
        }
    }
}