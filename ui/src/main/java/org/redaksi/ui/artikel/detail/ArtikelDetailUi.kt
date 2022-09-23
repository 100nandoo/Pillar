package org.redaksi.ui.artikel.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import org.redaksi.core.helper.JsoupHelper
import org.redaksi.core.helper.ReadingTime
import org.redaksi.data.remote.*
import org.redaksi.data.remote.response.base.Article
import org.redaksi.ui.R
import org.threeten.bp.ZonedDateTime

data class ArtikelDetailUi(
    val title: String = "",
    val authors: String = "",
    val zonedDateTime: ZonedDateTime = ZonedDateTime.now(),
    val estimation: String = "",
    val categoryUi: List<CategoryUi> = listOf(),
    val body: String = "",
    val bodyStriped: String = "",
)

data class CategoryUi(
    val label: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_transkrip
)

fun fromResponse(article: Article): ArtikelDetailUi {
    val authors = article.postAuthors.joinToString { it.name }

    val categoryUi = article.categories.map { it.toCategoryUi() }
    val estimation = ReadingTime(article.content.rendered).calcReadingTime()
    val title = JsoupHelper.stripText(article.title.rendered)

    val bodyStriped = title + "\n\nDitulis oleh: $authors\n\n" + JsoupHelper.stripText(article.content.rendered)

    return ArtikelDetailUi(
        title,
        authors,
        estimation = estimation,
        categoryUi = categoryUi,
        body = article.content.rendered,
        bodyStriped = bodyStriped,
    )
}

fun Int.toCategoryUi(): CategoryUi {
    val title = when(this){
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
