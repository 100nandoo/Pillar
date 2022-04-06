package org.redaksi.data.remote.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comments(
    val total: Int,
    val items: List<Comment>
)

@Serializable
data class Comment(
    @SerialName("_id")
    val id: String,
    @SerialName("article_id")
    val articleId: Int,
    val senderName: String,
    val senderCity: String,
    val senderEmail: String,
    val body: String,
    val createTime: Int,
    val status: String
)