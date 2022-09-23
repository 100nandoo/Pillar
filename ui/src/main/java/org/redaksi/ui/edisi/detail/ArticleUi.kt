package org.redaksi.ui.edisi.detail

import android.util.Log
import org.redaksi.core.helper.JsoupHelper
import org.redaksi.data.remote.response.base.NewArticle
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

data class ArticleUi(
    val id: Int = 0,
    val title: String = "",
    val body: String = "",
    val authors: String = "",
    val zonedDateTime: ZonedDateTime? = ZonedDateTime.now()
)

fun detailScreenDate(zonedDateTime: ZonedDateTime): String {
    return zonedDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}

fun fromResponse(articles: List<NewArticle>): List<ArticleUi> {
    return articles.map { article ->
        val authors = article.postAuthors.joinToString { it.name }
        val zonedDateTime = runCatching {
            ZonedDateTime.parse(article.date + "Z", DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .withZoneSameInstant(ZoneId.systemDefault())
        }.getOrNull()
        ArticleUi(article.id, JsoupHelper.stripText(article.title.rendered), JsoupHelper.stripText(article.content.rendered), authors, zonedDateTime)
    }
}
