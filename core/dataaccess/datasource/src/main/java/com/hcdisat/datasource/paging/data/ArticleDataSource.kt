package com.hcdisat.datasource.paging.data

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hcdisat.api.model.news.PagedArticle
import com.hcdisat.datasource.paging.paged
import com.hcdisat.networking.model.EverythingResponse
import com.hcdisat.networking.repositpry.EveryNewsRepository
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    private val everyNewsRepository: EveryNewsRepository
) : PagingSource<Int, PagedArticle>() {
    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 20
    }

    private val sources: AtomicReference<List<String>> = AtomicReference()
    private val articleCount: AtomicInteger = AtomicInteger(0)

    fun setSources(vararg source: String) {
        sources.set(source.toList())
    }

    override fun getRefreshKey(state: PagingState<Int, PagedArticle>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)

        return page?.nextKey?.minus(FIRST_PAGE) ?: page?.prevKey?.plus(FIRST_PAGE)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedArticle> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val news = makeCall(page).getOrThrow()
            val total = articleCount.updateAndGet { news.articles.size }
            val articles = news.articles.distinctBy { it?.title }.map { it.paged() }

            LoadResult.Page(
                data = articles,
                nextKey = if (total == news.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    private suspend fun makeCall(page: Int): Result<EverythingResponse> =
        everyNewsRepository.getBySources(
            sources = sources.get().toTypedArray(),
            page = page,
            pageSize = PAGE_SIZE
        )
}