package org.redaksi.data.remote.response

import kotlinx.serialization.Serializable
import org.redaksi.data.remote.response.base.Articles
import org.redaksi.data.remote.response.base.Issue

@Serializable
data class GenericIssueWithArticlesResponse(
    val issue: Issue,
    val articles: Articles
)