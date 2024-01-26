package com.hcdisat.datasource.paging

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hcdisat.api.paging.PagedArticle
import com.hcdisat.networking.model.EverythingResponse
import com.hcdisat.networking.service.EveryNewsService
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class ArticleDataSource @Inject constructor(
    private val newsService: EveryNewsService
) : PagingSource<Int, PagedArticle>() {
    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGE_SIZE = 20
    }

    private val sources: AtomicReference<String> = AtomicReference()
    private val articleCount: AtomicInteger = AtomicInteger(0)

    fun setSources(vararg source: String) {
        sources.set(source.joinToString(separator = ","))
    }

    override fun getRefreshKey(state: PagingState<Int, PagedArticle>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)

        return page?.nextKey?.minus(FIRST_PAGE) ?: page?.prevKey?.plus(FIRST_PAGE)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagedArticle> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val news = makeCall(page)
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

    private suspend fun makeCall(page: Int): EverythingResponse {
        val newsResponse = newsService.getBySources(
            sources = sources.get(),
            page = page,
            pageSize = PAGE_SIZE
        )

        val news = newsResponse.body()


        return if (newsResponse.isSuccessful && news != null)
            news
        else throw HttpException("response was not successfully", null)
    }
}