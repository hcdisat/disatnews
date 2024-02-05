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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.hcdisat.common.exceptions.MaxResultsReachedException
import com.hcdisat.presentation.NightDayPreview
import com.hcdisat.presentation.R
import com.hcdisat.presentation.getPagedArticles
import com.hcdisat.presentation.ui.model.Article
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DimenDefault.mediumPadding()),
        contentPadding = PaddingValues(all = DimenDefault.xSmallPadding()),
    ) {
        items(articles.itemCount) { index ->
            articles[index]?.let { article ->
                ArticleItem(article = article, onClick = { onClick(article) })
            }
        }
    }

    HandleState(state = articles.loadState.refresh)
    HandleState(state = articles.loadState.append)
}

@Composable
private fun HandleState(state: LoadState) {
    when (state) {
        is LoadState.Error -> EmptyScreen(errorMessage = parseErrorMessage(state.error))
        is LoadState.Loading -> ArticleShimmer()
        is LoadState.NotLoading -> Unit
    }
}

@Composable
private fun parseErrorMessage(error: Throwable?): String {
    return when (error) {
        is SocketTimeoutException -> stringResource(R.string.error_server_unavailable)
        is ConnectException -> stringResource(R.string.error_internet_unavailable)
        is MaxResultsReachedException -> error.message
        else -> stringResource(R.string.error_unknown_error)
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

@NightDayPreview
@Composable
private fun RenderArticlesPreview() {
    CompositionLocalProvider(
        LocalInspectionMode provides true,
    ) {
        val lazyPagingArticles = getPagedArticles(count = 5)
        DisatNewsTheme {
            ArticleList(articles = lazyPagingArticles) {}
        }
    }
}