package com.hcdisat.news.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hcdisat.common.exceptions.MaxResultsReachedException
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.entity.PagedArticles
import com.hcdisat.news.domain.repository.EveryNewsRepository
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class ArticleDataSource(
    private val everyNewsRepository: EveryNewsRepository,
    private val pageSize: Int
) : PagingSource<Int, PagedArticle>() {
    companion object {
        private const val FIRST_PAGE = 1
    }

    private val sources: AtomicReference<List<String>> = AtomicReference()
    private val articleCount: AtomicInteger = AtomicInteger(0)

    fun setSources(vararg source: String) {
        sources.set(source.toList())
    }

    override fun getRefreshKey(state: PagingState<Int, PagedArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            return page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedArticle> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val result = makeCall(page).getOrThrow()
            val total = articleCount.updateAndGet { it + result.articles.size }
            LoadResult.Page(
                data = result.articles,
                prevKey = null,
                nextKey = if (total == result.totalResults) null else page + 1,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: MaxResultsReachedException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private suspend fun makeCall(page: Int): Result<PagedArticles> =
        everyNewsRepository.getBySources(
            sources = sources.get().toTypedArray(),
            page = page,
            pageSize = pageSize
        )
}