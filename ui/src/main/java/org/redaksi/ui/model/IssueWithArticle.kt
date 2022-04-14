package org.redaksi.ui.model

import org.redaksi.data.remote.response.AllIssuesResponse

data class IssueWithArticle(
    val number: String,
    val dateDisplay: String,
    val title: String,
    val articles: List<String>
)

fun fromResponse(response: AllIssuesResponse): List<IssueWithArticle> {
    val issues = response.issues.items

    return issues.map { issue ->
        val articles = issue.articleTitles.items
        IssueWithArticle(issue.issueNumber, issue.monthDisplay, issue.title ?: "Belum Ada Judul", articles)
    }
}
