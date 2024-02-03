package com.hcdisat.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hcdisat.presentation.NightDayPreview
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.model.Article
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>
) {
    when (val state = handlePagingResult(articles.loadState)) {
        is ListState.AppendLoading -> Unit
        is ListState.Error -> EmptyScreen(errorMessage = parseErrorMessage(state.throwable))
        is ListState.RefreshLoading -> ArticleShimmer()
        is ListState.Completed ->
            RenderArticles(
                articles = articles,
                modifier = modifier
            )
    }
}

@Composable
private fun RenderArticles(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DimenDefault.mediumPadding()),
        contentPadding = PaddingValues(all = DimenDefault.xSmallPadding())
    ) {
        items(articles.itemCount) { index ->
            val article = articles[index] ?: return@items
            ArticleItem(article = article) {}
        }
    }
}

@Composable
private fun parseErrorMessage(error: Throwable?): String {
    return when (error) {
        is SocketTimeoutException -> stringResource(R.string.error_server_unavailable)
        is ConnectException -> stringResource(R.string.error_internet_unavailable)
        else -> stringResource(R.string.error_unknown_error)
    }
}

private fun handlePagingResult(
    loadState: CombinedLoadStates
): ListState {
    val refreshState = loadState.refresh
    val appendState = loadState.append

    return when {
        refreshState is LoadState.Loading -> ListState.RefreshLoading
        appendState is LoadState.Loading -> ListState.AppendLoading
        refreshState is LoadState.Error -> ListState.Error(refreshState.error)
        appendState is LoadState.Error -> ListState.Error(appendState.error)
        else -> ListState.Completed
    }
}

sealed interface ListState {
    data object Completed : ListState
    data object RefreshLoading : ListState
    data object AppendLoading : ListState
    data class Error(val throwable: Throwable) : ListState
}

@Preview(showBackground = true)
@Composable
private fun Shimmer() {
    Column(verticalArrangement = Arrangement.spacedBy(DimenDefault.mediumPadding())) {
        repeat(10) {
            ArticleShimmer(Modifier.padding(horizontal = DimenDefault.mediumPadding()))
        }
    }
}

@NightDayPreview
@Composable
private fun RenderArticlesPreview() {
    val articles = listOf(
        Article(
            title = "Title 1",
            sourceName = "Source 1",
            publishedAt = "2021-08-01T00:00:00Z"
        ),
        Article(
            title = "Title 2",
            sourceName = "Source 2",
            publishedAt = "2021-08-01T00:00:00Z"
        ),
        Article(
            title = "Title 3",
            sourceName = "Source 3",
            publishedAt = "2021-08-01T00:00:00Z"
        ),
        Article(
            title = "Title 4",
            sourceName = "Source 4",
            publishedAt = "2021-08-01T00:00:00Z"
        ),
    )
    val flow = MutableStateFlow(PagingData.from(articles))
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        // Use StandardTestDispatcher so we don't start collecting on PagingData
        val lazyPagingArticles = flow.collectAsLazyPagingItems(Dispatchers.Main)
        DisatNewsTheme {
            ArticleList(articles = lazyPagingArticles)
        }
    }
}