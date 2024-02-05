package com.hcdisat.news.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.usecases.LoadNewsUseCase
import com.hcdisat.presentation.ui.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
internal class NewsViewModel @Inject constructor(
    loadNews: LoadNewsUseCase
) : ViewModel() {
    companion object {
        private val SOURCES = listOf("abc-news", "msnbc", "fox-news")
    }

    val newsPaging = loadNews(*SOURCES.toTypedArray())
        .map { pagingData -> pagingData.map { it.toArticle() } }
        .cachedIn(viewModelScope)

    private fun PagedArticle.toArticle() = Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        sourceId = source.id,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}