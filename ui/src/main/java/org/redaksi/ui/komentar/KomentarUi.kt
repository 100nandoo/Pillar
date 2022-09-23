package org.redaksi.ui.komentar

import org.redaksi.core.extension.capitalizeEveryWord
import org.redaksi.data.remote.response.GenericCommentsResponse
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

data class KomentarUi(val body: String, val author: String, val city: String, val zonedDateTime: ZonedDateTime, val articleId: Int)

fun fromResponse(response: GenericCommentsResponse): List<KomentarUi> {
    val comments = response.comments.items

    return comments.map { comment ->
        val zonedDateTime = ZonedDateTime.now()
            .withZoneSameInstant(ZoneId.systemDefault())
        KomentarUi(
            comment.body,
            comment.senderName.capitalizeEveryWord(),
            comment.senderCity.capitalizeEveryWord(),
            zonedDateTime,
            comment.articleId
        )
    }
}
