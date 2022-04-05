package org.redaksi.data.remote.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("_id")
    val id: String,
    val name: String,
    val title: String,
    val monthly: Boolean,
    val description: String?,
    val eternal: Boolean,
    val commentable: Boolean,
    val hidden: Boolean
)
