package com.hcdisat.api.repository

import androidx.paging.PagingData
import com.hcdisat.api.paging.PagedArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAll(vararg source: String): Flow<PagingData<PagedArticle>>
}