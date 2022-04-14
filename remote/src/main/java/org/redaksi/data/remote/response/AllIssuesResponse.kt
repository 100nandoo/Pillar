package org.redaksi.data.remote.response

import kotlinx.serialization.Serializable
import org.redaksi.data.remote.response.base.Issues

@Serializable
data class AllIssuesResponse(
    val issues: Issues
)
