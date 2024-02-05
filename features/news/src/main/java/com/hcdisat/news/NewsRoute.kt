package com.hcdisat.news

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.hcdisat.common.navigation.NewsEvent
import com.hcdisat.common.navigation.Route
import com.hcdisat.news.model.NewsAction
import com.hcdisat.news.screen.NewsScreen
import com.hcdisat.news.screen.NewsViewModel
import javax.inject.Inject

interface NewsRoute : Route<NewsEvent>

class NewsRouteImpl @Inject constructor() : NewsRoute {
    override val route: String get() = "news"

    override fun register(builder: NavGraphBuilder, onEvent: (NewsEvent) -> Unit) {
        builder.composable(route = route) {
            val viewModel: NewsViewModel = hiltViewModel()
            val pagedArticles = viewModel.newsPaging.collectAsLazyPagingItems()
            NewsScreen(articles = pagedArticles) {
                when (it) {
                    is NewsAction.OpenArticle -> onEvent(NewsEvent.OpenArticle)
                    is NewsAction.OpenSearch -> onEvent(NewsEvent.OpenSearch)
                }
            }
        }
    }
}