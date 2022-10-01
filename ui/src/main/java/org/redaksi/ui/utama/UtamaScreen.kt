package org.redaksi.ui.utama

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R
import org.redaksi.ui.artikel.ArticleItem

@Composable
fun UtamaScreen(paddingValues: PaddingValues, onClick: (artikelId: Int) -> Unit) {
    Scaffold { it ->
        val viewModel: UtamaViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        SwipeRefresh(
            modifier = Modifier
                .padding(paddingValues)
                .padding(it),
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.loadUtama() }
        ) {
            if (uiState.isLoading) {
                LoadingScreen(false)
            } else {
                LazyColumn {
                    item {
                        HeaderItem(Modifier.background(PillarColor.background), R.string.artikel_terbaru)
                    }
                    uiState.newestArticles.forEach { articleUi ->
                        item {
                            ArticleItem(articleUi = articleUi, isLast = false) { onClick(articleUi.id) }
                        }
                    }
                    item {
                        HeaderItem(Modifier.background(PillarColor.background), R.string.pilihan_editor)
                    }
                    uiState.editorChoiceArticles.forEachIndexed { index, articleUi ->
                        item {
                            val isLast = index == uiState.editorChoiceArticles.size - 1
                            ArticleItem(articleUi = articleUi, isLast = isLast) { onClick(articleUi.id) }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "UtamaScreen",
    showSystemUi = true
)
@Composable
private fun UtamaScreenPreview() {
    UtamaScreen(PaddingValues()) {}
}

@Composable
fun HeaderItem(modifier: Modifier, @StringRes id: Int) {
    val staticModifier = modifier
        .background(PillarColor.background)
        .padding(sixteen.dp, sixteen.dp, sixteen.dp, 0.dp)
        .fillMaxWidth()
    Text(
        modifier = staticModifier,
        style = PillarTypography3.headlineMedium,
        color = PillarColor.utamaTitle,
        textAlign = TextAlign.Center,
        text = stringResource(id = id)
    )
}

@Preview(
    name = "HeaderItem",
    showSystemUi = true
)
@Composable
private fun HeaderItemPreview() {
    Column {
        HeaderItem(Modifier, R.string.artikel_terbaru)
        HeaderItem(Modifier, R.string.pilihan_editor)

        HeaderItem(Modifier, R.string.artikel_terbaru)
        HeaderItem(Modifier, R.string.pilihan_editor)
    }
}
