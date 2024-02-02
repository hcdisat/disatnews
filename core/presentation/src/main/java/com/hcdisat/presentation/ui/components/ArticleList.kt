package com.hcdisat.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.hcdisat.api.model.news.PagedArticle
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.theme.DimenDefault
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ArticleList(
    modifier: Modifier,
    articles: LazyPagingItems<PagedArticle>
) {
    when (val state = handlePagingResult(articles.loadState)) {
        is ListState.AppendLoading -> Unit
        is ListState.Completed -> TODO()
        is ListState.Error -> EmptyScreen(errorMessage = parseErrorMessage(state.throwable))
        is ListState.RefreshLoading -> ArticleShimmer()
    }
}

@Composable
private fun RenderArticles(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<PagedArticle>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DimenDefault.mediumPadding()),
        contentPadding = PaddingValues(all = DimenDefault.xSmallPadding())
    ) {
        items(articles.itemCount) { index ->
            val article = articles[index] ?: return@items
            ArticleItem(article = article)
        }
    }
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