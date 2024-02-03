package com.hcdisat.news.domain.repository

import com.hcdisat.news.domain.entity.PagedArticles

interface EveryNewsRepository {
    suspend fun getBySources(
        vararg sources: String,
        page: Int,
        pageSize: Int
    ): Result<PagedArticles>
}