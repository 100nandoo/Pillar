package org.redaksi.ui.utama

import org.redaksi.core.helper.JsoupHelper
import org.redaksi.data.remote.response.base.Article
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

data class ArticleUi(
    val id: Int = 0,
    val title: String = "",
    val body: String = "",
    val authors: String = "",
    val displayDate: String = "",
    val imageUrl: String = ""
)

fun detailScreenDate(zonedDateTime: ZonedDateTime?): String {
    return zonedDateTime?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)) ?: ""
}

fun fromResponse(articles: List<Article>): List<ArticleUi> {
    return articles.map { article ->
        val authors = article.postAuthors.joinToString { it.name }

        val zonedDateTime = runCatching {
            ZonedDateTime.parse(article.date + "Z", DateTimeFormatter.ISO_ZONED_DATE_TIME)
                .withZoneSameInstant(ZoneId.systemDefault())
        }.getOrNull()
        val displayDate = detailScreenDate(zonedDateTime)

        ArticleUi(
            article.id,
            JsoupHelper.stripText(article.title.rendered),
            article.searchContent,
            authors,
            displayDate,
            article.jetpackFeaturedMediaUrl
        )
    }
}
