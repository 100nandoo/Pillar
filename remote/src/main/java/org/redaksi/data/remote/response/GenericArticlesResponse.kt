package org.redaksi.data.remote.response


import kotlinx.serialization.Serializable
import org.redaksi.data.remote.response.base.Articles

@Serializable
data class GenericArticlesResponse(
    val articles: Articles
)