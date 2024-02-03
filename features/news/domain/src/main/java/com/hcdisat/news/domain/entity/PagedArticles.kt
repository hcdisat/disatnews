package com.hcdisat.news.domain.entity

data class PagedArticles(
    val articles: List<PagedArticle>,
    val totalResults: Int
)