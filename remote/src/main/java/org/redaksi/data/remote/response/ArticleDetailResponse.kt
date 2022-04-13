package org.redaksi.data.remote.response

import kotlinx.serialization.Serializable
import org.redaksi.data.remote.response.base.Article

@Serializable
data class ArticleDetailResponse(
    val article: Article
)
