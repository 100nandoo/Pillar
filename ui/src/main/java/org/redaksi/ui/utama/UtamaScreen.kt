@file:OptIn(ExperimentalMaterialApi::class)

package org.redaksi.ui.utama

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.R
import org.redaksi.ui.artikel.ArticleItem
import org.redaksi.ui.compose.PillarColor
import org.redaksi.ui.compose.PillarColor.primary
import org.redaksi.ui.compose.PillarTypography3
import org.redaksi.ui.compose.UiModelProvider

@Composable
fun UtamaScreen(paddingValues: PaddingValues, onClick: (artikelId: Int) -> Unit) {
    val viewModel: UtamaViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    UtamaScreenContent(
        uiState = uiState,
        paddingValues = paddingValues,
        onClick = { onClick(it) },
        onRefresh = { viewModel.onEvent(UtamaEvent.LoadUtama) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun UtamaScreenContent(uiState: UtamaViewModelState, paddingValues: PaddingValues, onClick: (artikelId: Int) -> Unit, onRefresh: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_pillar),
                        contentDescription = stringResource(R.string.logo_pillar)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primary)
            )
        }
    ) {
        val pullRefreshState = rememberPullRefreshState(refreshing = uiState.isLoading, onRefresh = { onRefresh() })
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            if (uiState.isLoading) {
                LoadingScreen(isLoading = false)
            } else {
                LazyColumn {
                    item {
                        HeaderItem(Modifier.background(PillarColor.background), R.string.artikel_terbaru)
                    }
                    uiState.newestArticles.forEach { articleUi ->
                        item {
                            ArticleItem(articleUi = articleUi, isDividerShown = false) { onClick(articleUi.id) }
                        }
                    }
                    item {
                        HeaderItem(Modifier.background(PillarColor.background), R.string.pilihan_editor)
                    }
                    uiState.editorChoiceArticles.forEach { articleUi ->
                        item {
                            ArticleItem(articleUi = articleUi, isDividerShown = false) { onClick(articleUi.id) }
                        }
                    }
                }
            }
            PullRefreshIndicator(true, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Preview(
    name = "Utama Screen"
)
@Composable
private fun UtamaScreenPreview() {
    UtamaScreenContent(
        UtamaViewModelState(
            newestArticles = UiModelProvider.articleUiList,
            editorChoiceArticles = UiModelProvider.articleUiList,
            false
        ),
        PaddingValues(),
        {}
    ) {}
}

@Composable
fun HeaderItem(modifier: Modifier, @StringRes id: Int) {
    val staticModifier = modifier
        .background(PillarColor.background)
        .padding(sixteen.dp)
        .fillMaxWidth()
    Text(
        modifier = staticModifier,
        style = PillarTypography3.headlineMedium,
        color = PillarColor.utamaTitle,
        textAlign = TextAlign.Left,
        text = stringResource(id = id)
    )
}

@Preview(
    name = "HeaderItem"
)
@Composable
private fun HeaderItemPreview() {
    Column {
        HeaderItem(Modifier, R.string.artikel_terbaru)
        HeaderItem(Modifier, R.string.pilihan_editor)
    }
}
