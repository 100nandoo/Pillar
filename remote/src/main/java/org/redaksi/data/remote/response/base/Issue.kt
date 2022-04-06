package org.redaksi.data.remote.response.base

import kotlinx.serialization.Serializable

@Serializable
data class Issues(
    val total: Int,
    val items: List<Issue>
)

@Serializable
data class Issue(
    val issueNumber: String,
    val yyyymm: String,
    val monthDisplay: String,
    val snippet: String?,
    val title: String?,
    val thumbnailUrl: String
)
