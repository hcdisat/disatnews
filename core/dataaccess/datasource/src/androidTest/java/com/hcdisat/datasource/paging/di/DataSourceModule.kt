package com.hcdisat.datasource.paging.di

import androidx.paging.PagingSource
import com.hcdisat.api.paging.PagedArticle
import com.hcdisat.datasource.paging.data.ArticleDataSource
import com.hcdisat.datasource.paging.repository.PagedArticleRepository
import com.hcdisat.datasource.paging.repository.PagedArticleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface DataSourceModule {
    @Binds
    @ViewModelScoped
    fun bindsArticleDataSource(impl: ArticleDataSource): PagingSource<Int, PagedArticle>

    @Binds
    @ViewModelScoped
    fun bindsPagedArticleRepository(impl: PagedArticleRepositoryImpl): PagedArticleRepository
}