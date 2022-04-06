package org.redaksi.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertCommentResponse(
    val ok: Boolean,
    @SerialName("comment_id")
    val commentId: Int,
    val message: String?
)