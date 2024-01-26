package com.hcdisat.networking.model

import com.google.gson.annotations.SerializedName

abstract class BaseResponse {
    val code: String? = null
    val message: String? = null

    fun maximumResultsReached(): Boolean {
        return code != null && code == "maximumResultsReached"
    }
}


data class EverythingResponse(
    @SerializedName("articles")
    val articles: List<Article?>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) : BaseResponse()

data class Source(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)

data class Article(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source = Source("", ""),
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
)