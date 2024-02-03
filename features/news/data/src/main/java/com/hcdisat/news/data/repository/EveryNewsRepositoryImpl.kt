package com.hcdisat.news.data.repository

import com.hcdisat.networking.service.EveryNewsService
import com.hcdisat.news.data.paged
import com.hcdisat.news.domain.entity.PagedArticles
import com.hcdisat.news.domain.repository.EveryNewsRepository
import javax.inject.Inject

class EveryNewsRepositoryImpl @Inject constructor(
    private val newsService: EveryNewsService
) : EveryNewsRepository {
    override suspend fun getBySources(
        vararg sources: String,
        page: Int,
        pageSize: Int
    ): Result<PagedArticles> = runCatching {
        val response = newsService.getBySources(
            sources = sources.joinToString(separator = ","),
            page = page,
            pageSize = pageSize
        )

        val news = response.body()
        if (response.isSuccessful && news != null) {
            val articles = news.articles.distinctBy { it?.title }.map { it.paged() }
            PagedArticles(articles = articles, totalResults = news.totalResults)
        } else {
            throw RuntimeException("every news response was empty or unsuccessful")
        }
    }
}