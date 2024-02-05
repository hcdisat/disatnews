package com.hcdisat.news.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.hcdisat.news.components.NewsDimen
import com.hcdisat.news.model.NewsAction
import com.hcdisat.presentation.R
import com.hcdisat.presentation.getPagedArticles
import com.hcdisat.presentation.ui.components.AppLogo
import com.hcdisat.presentation.ui.components.ArticleList
import com.hcdisat.presentation.ui.components.SearchBar
import com.hcdisat.presentation.ui.model.Article
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun NewsScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    sendAction: (NewsAction) -> Unit = {}
) {
    val titles by remember { derivedStateOf { articles.titles() } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = NewsDimen.smallPadding())
            .padding(horizontal = NewsDimen.xSmallPadding())
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(NewsDimen.mediumPadding()),
    ) {
        AppLogo(
            width = NewsDimen.logoWidth(),
            height = NewsDimen.logoHeight(),
            modifier = Modifier.padding(horizontal = NewsDimen.smallPadding())
        )

        SearchBar(
            readOnly = true,
            onClick = { sendAction(NewsAction.OpenSearch) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = NewsDimen.smallPadding())
        )

        Text(
            text = titles,
            style = MaterialTheme.typography.bodySmall.copy(
                color = colorResource(id = R.color.placeholder)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = NewsDimen.smallPadding())
                .basicMarquee(),
        )

        ArticleList(
            articles = articles,
            modifier = Modifier.padding(start = NewsDimen.smallPadding()),
        ) {
            sendAction(NewsAction.OpenArticle(it))
        }
    }
}

private fun LazyPagingItems<Article>.titles(): String {
    val emoji = " \uD83D\uDFE5 "
    return if (itemCount > 10) {
        itemSnapshotList.items
            .slice(IntRange(start = 0, endInclusive = 9))
            .joinToString(separator = " $emoji ") { it.title }
    } else {
        ""
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NewsScreenPreview() {
    val articles = getPagedArticles(count = 12)
    DisatNewsTheme {
        NewsScreen(articles = articles)
    }
}