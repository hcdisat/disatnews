package com.hcdisat.datasource.paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hcdisat.api.paging.PagedArticle
import com.hcdisat.datasource.paging.data.ArticleDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PagedArticleRepository {
    fun getAll(vararg source: String): Flow<PagingData<PagedArticle>>
}

class PagedArticleRepositoryImpl @Inject constructor(
    private val dataSource: ArticleDataSource
) : PagedArticleRepository {
    override fun getAll(vararg source: String): Flow<PagingData<PagedArticle>> {
        dataSource.setSources(*source)
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { dataSource }
        ).flow
    }
}