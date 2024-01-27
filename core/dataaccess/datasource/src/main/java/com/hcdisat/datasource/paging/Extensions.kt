package com.hcdisat.datasource.paging

import com.hcdisat.api.model.news.PagedArticle
import com.hcdisat.api.model.news.PagedSource
import com.hcdisat.networking.model.Article

fun Article?.paged(): PagedArticle {
    if (this == null) return PagedArticle()

    return PagedArticle(
        author = author.orEmpty(),
        content = content.orEmpty(),
        description = description.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        source = PagedSource(id = source.id.orEmpty(), name = source.name.orEmpty()),
        title = title.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
    )
}