package com.hcdisat.news.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.entity.PagedArticles
import com.hcdisat.news.domain.repository.EveryNewsRepository
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
            val result = makeCall(page).getOrThrow()
            val total = articleCount.updateAndGet { result.articles.size }

            LoadResult.Page(
                data = result.articles,
                nextKey = if (total == result.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    private suspend fun makeCall(page: Int): Result<PagedArticles> =
        everyNewsRepository.getBySources(
            sources = sources.get().toTypedArray(),
            page = page,
            pageSize = PAGE_SIZE
        )
}