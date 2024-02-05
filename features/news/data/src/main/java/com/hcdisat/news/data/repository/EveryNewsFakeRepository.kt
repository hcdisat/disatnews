package com.hcdisat.news.data.repository

import android.content.Context
import com.google.gson.Gson
import com.hcdisat.networking.model.Article
import com.hcdisat.networking.model.EverythingResponse
import com.hcdisat.news.data.paged
import com.hcdisat.news.domain.entity.PagedArticles
import com.hcdisat.news.domain.repository.EveryNewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class EveryNewsFakeRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : EveryNewsRepository {
    private var cachedResponse: AtomicReference<List<List<Article?>>> = AtomicReference()

    override suspend fun getBySources(
        vararg sources: String,
        page: Int,
        pageSize: Int
    ): Result<PagedArticles> {
        if (cachedResponse.get() == null) {
            chuckResponse(pageSize)
        }
        return Result.success(
            PagedArticles(
                articles = cachedResponse.get()[page].map { it.paged() },
                totalResults = 100
            )
        )
    }

    private inline fun <reified T> parseResponse(): T {
        val gson = Gson()
        val assetsManager = context.assets
        val response = assetsManager.open("responses/response.json").bufferedReader().use {
            gson.fromJson(it, T::class.java)
        }

        return response
    }

    private fun chuckResponse(pageSize: Int) {
        val rawResponse = parseResponse<EverythingResponse>()
        cachedResponse.set(rawResponse.articles.chunked(pageSize))
    }
}