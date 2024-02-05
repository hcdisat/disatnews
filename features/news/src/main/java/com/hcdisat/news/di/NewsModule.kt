package com.hcdisat.news.di

import com.hcdisat.common.di.EveryNewsRepositoryNetwork
import com.hcdisat.common.di.EveryNewsRepositoryNetworkFake
import com.hcdisat.news.data.repository.EveryNewsFakeRepository
import com.hcdisat.news.data.repository.EveryNewsRepositoryImpl
import com.hcdisat.news.data.repository.PagedArticleRepositoryImpl
import com.hcdisat.news.domain.repository.EveryNewsRepository
import com.hcdisat.news.domain.repository.PagedArticleRepository
import com.hcdisat.news.domain.usecases.LoadNewsUseCase
import com.hcdisat.news.domain.usecases.LoadNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface NewsModule {
    @Binds
    @ViewModelScoped
    fun bindsLoadNewsUseCase(impl: LoadNewsUseCaseImpl): LoadNewsUseCase

    @Binds
    @EveryNewsRepositoryNetwork
    fun bindsEveryNewsRepository(impl: EveryNewsRepositoryImpl): EveryNewsRepository

    @Binds
    @EveryNewsRepositoryNetworkFake
    fun bindsEveryNewsRepositoryFake(impl: EveryNewsFakeRepository): EveryNewsRepository

    @Binds
    @ViewModelScoped
    fun bindsPagedArticleRepository(impl: PagedArticleRepositoryImpl): PagedArticleRepository
}