package com.hcdisat.api.model.news

data class PagedSource(
    val id: String = "",
    val name: String = ""
)

data class PagedArticle(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: PagedSource = PagedSource(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = ""
)