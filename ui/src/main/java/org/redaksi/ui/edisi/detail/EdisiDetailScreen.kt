package org.redaksi.ui.edisi.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.edisiDetailBody
import org.redaksi.ui.PillarTypography
import org.redaksi.ui.R
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdisiDetailScreen(
    modifier: PaddingValues,
    issueNumber: String,
    viewModel: EdisiDetailViewModel = hiltViewModel(),
    onClick: (artikelId: Int) -> Unit
) {
    viewModel.loadArticles(issueNumber)

    Scaffold(
        Modifier.padding(modifier)
    ) {
        val uiState by viewModel.uiState.collectAsState()
        LazyColumn {
            if (uiState.isLoading) {
                List(8) { ArticleUi() }.forEach { articleUi ->
                    item {
                        ArticleItem(articleUi = articleUi, isLast = false) { onClick(articleUi.id) }
                    }
                }
            }
            uiState.articlesUi.forEachIndexed { index, articleUi ->
                item {
                    val isLast = index == uiState.articlesUi.size-1
                    ArticleItem(articleUi = articleUi, isLast = isLast) { onClick(articleUi.id) }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EdisiDetailScreenPreview() {
    EdisiDetailScreen(issueNumber = "", modifier = PaddingValues()) {}
}

@Composable
fun ArticleItem(modifier: Modifier = Modifier, articleUi: ArticleUi, isLast: Boolean, onClick: (artikelId: Int) -> Unit) {
    val paddingTop = modifier.padding(0.dp, eight.dp, 0.dp, 0.dp)
    Column(
        modifier
            .padding(sixteen.dp, eight.dp, sixteen.dp, 0.dp)
            .clickable { onClick(articleUi.id) }) {
        Text(style = PillarTypography.headlineSmall, text = articleUi.title)
        if (articleUi.body.isNotBlank()) {
            Text(
                modifier = paddingTop,
                style = PillarTypography.bodyMedium,
                color = edisiDetailBody,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = articleUi.body
            )
        }
        Row(modifier = paddingTop) {
            Text(
                modifier = Modifier
                    .weight(1f),
                style = PillarTypography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = stringResource(id = R.string.oleh) + " " + articleUi.authors
            )
            Text(
                style = PillarTypography.labelSmall,
                text = detailScreenDate(LocalContext.current, articleUi.date)
            )
        }
        if (isLast.not()) {
            Divider(modifier = paddingTop, color = PillarColor.secondaryVar)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        Modifier,

        ArticleUi(
            0,
            "Doktrin Wahyu: Sebuah Introduksi",
            "Bab pertama buku ini dimulai dengan penjelasan tentang aksiologi (teori nilai) dan hubungan nyata",
            "John Doe",
            Date()
        ),
        false
    ) {}
}
