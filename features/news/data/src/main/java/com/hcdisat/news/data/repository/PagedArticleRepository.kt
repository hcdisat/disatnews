package com.hcdisat.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hcdisat.news.data.ArticleDataSource
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.repository.PagedArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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