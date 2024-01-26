package com.hcdisat.datasource.paging.data

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hcdisat.api.paging.PagedArticle
import com.hcdisat.networking.model.EverythingResponse
import com.hcdisat.networking.service.EveryNewsService
import java.io.IOException
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator @Inject constructor(
    private val newsService: EveryNewsService
) : RemoteMediator<Int, PagedArticle>() {
    companion object {
        private const val FIRST_PAGE = 1
        private const val PREPEND = -1
    }

    private val sources: AtomicReference<String> = AtomicReference()

    fun setSources(vararg source: String) {
        sources.set(source.joinToString(separator = ","))
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PagedArticle>
    ): MediatorResult {
        val nextPage = getNextPage(loadType, state)
        if (nextPage == PREPEND) return MediatorResult.Success(endOfPaginationReached = true)

        return try {
            val news = makeCall(nextPage, state.config.pageSize)
            MediatorResult.Success(
                endOfPaginationReached = news.maximumResultsReached()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun makeCall(page: Int, pageSize: Int): EverythingResponse {
        val newsResponse = newsService.getBySources(
            sources = sources.get(),
            page = page,
            pageSize = pageSize
        )

        val news = newsResponse.body()

        return if (newsResponse.isSuccessful && news != null)
            news
        else throw HttpException("response was not successfully", null)
    }

    private fun getNextPage(
        loadType: LoadType,
        state: PagingState<Int, PagedArticle>
    ): Int = when (loadType) {
        LoadType.REFRESH -> FIRST_PAGE
        LoadType.PREPEND -> PREPEND
        LoadType.APPEND -> state.anchorPosition?.let { it + 1 } ?: FIRST_PAGE
    }
}