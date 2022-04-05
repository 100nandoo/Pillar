package org.redaksi.data.remote.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val total: Int,
    val items: List<Article>
)

@Serializable
data class Article(
    @SerialName("_id")
    val id: Int,
    val category: Category,
    val name: String,
    val title: String,
    val createTime: Int,
    val body: String?,
    val snippet: String,
    val url: String,
    val authors: Authors
)
