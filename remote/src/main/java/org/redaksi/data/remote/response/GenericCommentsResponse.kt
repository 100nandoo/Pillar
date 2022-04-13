package org.redaksi.data.remote.response

import kotlinx.serialization.Serializable
import org.redaksi.data.remote.response.base.Comments

@Serializable
data class GenericCommentsResponse(
    val comments: Comments
)
