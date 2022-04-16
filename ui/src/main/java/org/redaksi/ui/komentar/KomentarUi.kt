package org.redaksi.ui.komentar

import org.redaksi.core.extension.capitalizeEveryWord
import org.redaksi.data.remote.response.GenericCommentsResponse
import java.util.Date

data class KomentarUi(val body: String, val author: String, val city: String, val date: Date)

fun fromResponse(response: GenericCommentsResponse): List<KomentarUi> {
    val comments = response.comments.items

    return comments.map { comment ->
        KomentarUi(
            comment.body,
            comment.senderName.capitalizeEveryWord(),
            comment.senderCity.capitalizeEveryWord(),
            Date(comment.createTime.toLong() * 1000)
        )
    }
}
