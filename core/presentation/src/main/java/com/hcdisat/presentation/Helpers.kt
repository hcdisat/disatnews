package com.hcdisat.presentation

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hcdisat.presentation.ui.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

fun getArticles(count: Int): List<Article> {
    val articles = mutableListOf<Article>()
    repeat(count) {
        articles.add(
            Article(
                title = "Title $it",
                sourceName = "Source $it",
                publishedAt = "2021-08-01T00:00:00Z"
            ),
        )
    }

    return articles
}

@Composable
fun getPagedArticles(count: Int): LazyPagingItems<Article> =
    MutableStateFlow(PagingData.from(getArticles(count)))
        .collectAsLazyPagingItems(Dispatchers.Main)
