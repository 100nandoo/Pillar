package org.redaksi.ui.edisi

import org.redaksi.data.remote.response.AllIssuesResponse

data class IssueUi(
    val number: String,
    val dateDisplay: String,
    val title: String,
    val articles: List<String>
)

fun fromResponse(response: AllIssuesResponse): List<IssueUi> {
    val issues = response.issues.items
    val issuesUi = mutableListOf<IssueUi>()
    issues.forEach { issue ->
        val articles = issue.articleTitles.items
        if(articles.isNotEmpty()){
            issuesUi.add(IssueUi(issue.issueNumber, issue.monthDisplay, issue.title ?: "", articles))
        }
    }
    return issuesUi
}
