package org.redaksi.ui.artikel.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.redaksi.core.helper.ReadingTime
import org.redaksi.data.remote.response.ArticleDetailResponse
import org.redaksi.data.remote.response.base.Category
import org.redaksi.ui.R
import java.util.Date

data class ArtikelDetailUi(
    val title: String = "",
    val authors: String = "",
    val date: Date = Date(),
    val estimation: String = "",
    val categoryUi: CategoryUi = CategoryUi(),
    val body: String = ""
)

data class CategoryUi(
    val label: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_transkrip
)

fun fromResponse(response: ArticleDetailResponse): ArtikelDetailUi {
    val article = response.article
    val authors = article.authors.items.joinToString { it.title ?: "" }
    val categoryUi = article.category.toCategoryUi()
    val estimation = ReadingTime(response.article.body ?: "").calcReadingTime()
    return ArtikelDetailUi(article.title, authors, Date(article.createTime.toLong() * 1000), estimation, categoryUi, article.body ?: "")
}

fun Category.toCategoryUi(): CategoryUi {
    return CategoryUi(this.name.uppercase(), R.drawable.ic_transkrip)
}

data class BottomBarIcon(@DrawableRes val icon: Int, @StringRes val label: Int, val onClick: (Int) -> Unit)
