package com.hcdisat.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hcdisat.common.di.EveryNewsRepositoryNetworkFake
import com.hcdisat.news.data.ArticleDataSource
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.repository.EveryNewsRepository
import com.hcdisat.news.domain.repository.PagedArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagedArticleRepositoryImpl @Inject constructor(
    @EveryNewsRepositoryNetworkFake private val everyNewsRepository: EveryNewsRepository
) : PagedArticleRepository {
    override fun getAll(vararg source: String): Flow<PagingData<PagedArticle>> {
        val pageSize = 40
        val dataSource = ArticleDataSource(
            everyNewsRepository = everyNewsRepository,
            pageSize = pageSize
        ).apply { setSources(*source) }

        return Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { dataSource }
        ).flow
    }
}