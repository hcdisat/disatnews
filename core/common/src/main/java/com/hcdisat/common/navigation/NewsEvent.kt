package com.hcdisat.common.navigation

sealed interface NewsEvent {
    data object OpenArticle : NewsEvent
    data object OpenSearch : NewsEvent
}