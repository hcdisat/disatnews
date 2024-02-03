package com.hcdisat.news.domain.repository

import androidx.paging.PagingData
import com.hcdisat.news.domain.entity.PagedArticle
import kotlinx.coroutines.flow.Flow

interface PagedArticleRepository {
    fun getAll(vararg source: String): Flow<PagingData<PagedArticle>>
}