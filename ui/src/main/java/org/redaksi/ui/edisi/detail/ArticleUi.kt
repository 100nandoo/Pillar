package org.redaksi.ui.edisi.detail

import android.content.Context
import android.text.format.DateUtils
import org.redaksi.data.remote.MEJA_REDAKSI
import org.redaksi.data.remote.POKOK_DOA
import org.redaksi.data.remote.response.GenericArticlesResponse
import org.redaksi.data.remote.response.GenericIssueWithArticlesResponse
import java.util.Date

data class ArticleUi(val id: Int = 0, val title: String = "", val body: String = "", val authors: String = "", val date: Date = Date())

fun detailScreenDate(context: Context, date: Date): String {
    return DateUtils.formatDateTime(context, date.time, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_ABBREV_MONTH)
}

fun fromResponse(response: GenericIssueWithArticlesResponse): List<ArticleUi> {
    val articles = response.articles.items

    return articles.map { article ->
        val title = when (article.category.id.toIntOrNull()) {
            MEJA_REDAKSI -> "Meja Redaksi " + article.title
            POKOK_DOA -> "Pokok Doa " + article.title
            else -> article.title
        }
        val authors = article.authors.items.joinToString { it.title ?: "" }
        ArticleUi(article.id, title, article.body ?: "", authors, Date(article.createTime.toLong() * 1000))
    }
}

fun fromResponse(response: GenericArticlesResponse): List<ArticleUi> {
    val articles = response.articles.items

    return articles.map { article ->
        val authors = article.authors.items.joinToString { it.title ?: "" }
        ArticleUi(article.id, article.title, article.body ?: "", authors, Date(article.createTime.toLong() * 1000))
    }
}
