package com.hcdisat.news.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hcdisat.common.di.IO
import com.hcdisat.news.domain.usecases.LoadNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    loadNews: LoadNewsUseCase,
    @IO dispatcher: CoroutineDispatcher
) : ViewModel() {
    companion object {
        private val SOURCES = listOf("abc-news", "msnbc", "fox-news")
    }

    val newsPaging = loadNews(*SOURCES.toTypedArray())
        .flowOn(dispatcher)
        .cachedIn(viewModelScope)
}