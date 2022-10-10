package org.redaksi.ui.artikel.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import org.redaksi.core.helper.JsoupHelper
import org.redaksi.core.helper.ReadingTime
import org.redaksi.data.remote.ALKITAB_N_THEOLOGI
import org.redaksi.data.remote.ARTIKEL_MINGGUAN
import org.redaksi.data.remote.IMAN_KRISTEN
import org.redaksi.data.remote.IMAN_KRISTEN_N_PEKERJAAN
import org.redaksi.data.remote.ISU_TERKINI
import org.redaksi.data.remote.KEHIDUPAN_KRISTEN
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.SENI_BUDAYA
import org.redaksi.data.remote.SEPUTAR_GRII
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.data.remote.response.base.Article
import org.redaksi.ui.R
import org.redaksi.ui.utama.detailScreenDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

data class ArtikelDetailUi(
    val title: String = "",
    val authors: String = "",
    val displayDate: String = "",
    val estimation: String = "",
    val categoryUi: List<CategoryUi> = listOf(),
    val body: String = "",
    val bodyStriped: String = "",
    val imageUrl: String = ""
)

data class CategoryUi(
    val label: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_transkrip
)

fun fromResponse(article: Article): ArtikelDetailUi {
    val authors = article.postAuthors.joinToString { it.name }

    val zonedDateTime = runCatching {
        ZonedDateTime.parse(article.date + "Z", DateTimeFormatter.ISO_ZONED_DATE_TIME)
            .withZoneSameInstant(ZoneId.systemDefault())
    }.getOrNull()
    val displayDate = detailScreenDate(zonedDateTime)

    val categoryUi = article.categories.map { it.toCategoryUi() }
    val estimation = ReadingTime(article.content.rendered).calcReadingTime()
    val title = JsoupHelper.stripText(article.title.rendered)

    val bodyStriped = title + "\n\nDitulis oleh: $authors\n\n" + JsoupHelper.stripText(article.content.rendered)

    return ArtikelDetailUi(
        title = title,
        authors = authors,
        displayDate = displayDate,
        estimation = estimation,
        categoryUi = categoryUi,
        body = article.content.rendered,
        bodyStriped = bodyStriped,
        imageUrl = article.jetpackFeaturedMediaUrl
    )
}

fun Int.toCategoryUi(): CategoryUi {
    val title = when (this) {
        TRANSKIP -> ::TRANSKIP.name
        ALKITAB_N_THEOLOGI -> ::ALKITAB_N_THEOLOGI.name
        IMAN_KRISTEN -> ::IMAN_KRISTEN.name
        KEHIDUPAN_KRISTEN -> ::KEHIDUPAN_KRISTEN.name
        RENUNGAN -> ::RENUNGAN.name
        ISU_TERKINI -> ::ISU_TERKINI.name
        SENI_BUDAYA -> ::SENI_BUDAYA.name
        SEPUTAR_GRII -> ::SEPUTAR_GRII.name
        RESENSI -> ::RESENSI.name
        ARTIKEL_MINGGUAN -> ::ARTIKEL_MINGGUAN.name
        IMAN_KRISTEN_N_PEKERJAAN -> ::IMAN_KRISTEN_N_PEKERJAAN.name
        else -> ""
    }
    val titleCapitalize = title.toLowerCase(Locale.current).capitalize(Locale.current)
        .replace("_n_", " & ")
        .replace("_", " ")
    return CategoryUi(titleCapitalize, R.drawable.ic_transkrip)
}

data class BottomBarIcon(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val isComment: Boolean,
    val onClick: (ArtikelDetailViewModelState) -> Unit
)
