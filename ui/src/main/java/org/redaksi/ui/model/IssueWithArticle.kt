package org.redaksi.ui.model

import org.redaksi.data.remote.response.GenericIssueWithArticlesResponse

data class IssueWithArticle(
    val number: String,
    val dateDisplay: String,
    val title: String,
    val articles: List<String>
)

fun fromResponse(response: GenericIssueWithArticlesResponse): IssueWithArticle {
    val issue = response.issue
    val articles = response.articles.items.map { it.title }
    return IssueWithArticle(issue.issueNumber, issue.monthDisplay, issue.title ?: "Belum Ada Judul", articles)
}
