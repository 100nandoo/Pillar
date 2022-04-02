package org.redaksi.data.remote.response.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Authors(
    val total: Int,
    val items: List<Author>
)

@Serializable
data class Author(
    @SerialName("_id")
    val id: String,
    val name: String,
    val title: String
)
