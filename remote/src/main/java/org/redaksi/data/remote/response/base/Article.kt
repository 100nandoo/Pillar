package org.redaksi.data.remote.response.base


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val date: String,
    val modified: String,
    val slug: String,
    val status: String,
    val type: String,
    val link: String,
    val title: Title,
    val content: Content,
    val author: Int,
    @SerialName("featured_media")
    val featuredMedia: Int,
    @SerialName("comment_status")
    val commentStatus: String,
    @SerialName("ping_status")
    val pingStatus: String,
    val sticky: Boolean,
    val template: String,
    val format: String,
    val categories: List<Int>,
    @SerialName("Penulis")
    val penulis: List<Int>,
    @SerialName("jetpack_featured_media_url")
    val jetpackFeaturedMediaUrl: String,
    @SerialName("search_content")
    val searchContent: String,
    @SerialName("post_authors")
    val postAuthors: List<PostAuthor>
) {
    @Serializable
    data class Title(
        val rendered: String
    )

    @Serializable
    data class Content(
        val rendered: String,
        val `protected`: Boolean
    )

    @Serializable
    data class PostAuthor(
        @SerialName("term_id")
        val termId: Int,
        val name: String,
        val link: String
    )
}
