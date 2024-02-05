package com.hcdisat.news.model

import com.hcdisat.presentation.ui.model.Article

internal sealed interface NewsAction {
    data object OpenSearch : NewsAction
    data class OpenArticle(val article: Article) : NewsAction
}