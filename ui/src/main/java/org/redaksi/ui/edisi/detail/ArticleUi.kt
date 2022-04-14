package org.redaksi.ui.edisi.detail

import android.content.Context
import android.text.format.DateUtils
import org.redaksi.data.remote.response.GenericIssueWithArticlesResponse
import java.util.Date

data class ArticleUi(val title: String = "", val body: String = "", val authors: String = "", val date: Date = Date())

fun detailScreenDate(context: Context, date: Date): String {
    return DateUtils.formatDateTime(context, date.time, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH)
}

fun fromResponse(response: GenericIssueWithArticlesResponse): List<ArticleUi> {
    val articles = response.articles.items

    return articles.map { article ->
        val authors = article.authors.items.joinToString { it.title ?: "" }
        ArticleUi(article.title, article.body ?: "", authors, Date(article.createTime.toLong() * 1000))
    }
}
