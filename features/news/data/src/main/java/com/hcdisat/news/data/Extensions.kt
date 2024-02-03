package com.hcdisat.news.data

import com.hcdisat.networking.model.Article
import com.hcdisat.news.domain.entity.PagedArticle
import com.hcdisat.news.domain.entity.PagedSource

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