package com.hcdisat.domain.usecases

import androidx.paging.PagingData
import com.hcdisat.api.paging.PagedArticle
import com.hcdisat.datasource.paging.repository.PagedArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoadNewsUseCase {
    operator fun invoke(vararg sources: String): Flow<PagingData<PagedArticle>>
}

class LoadNewsUseCaseImpl @Inject constructor(
    private val pagedArticleRepository: PagedArticleRepository
) : LoadNewsUseCase {
    override fun invoke(vararg sources: String): Flow<PagingData<PagedArticle>> =
        pagedArticleRepository.getAll(*sources)
}