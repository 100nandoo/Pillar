package org.redaksi.ui.artikel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.redaksi.data.remote.ARTIKEL
import org.redaksi.data.remote.LAIN_LAIN
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.secondaryVar
import org.redaksi.ui.PillarColor.surface
import org.redaksi.ui.R
import org.redaksi.ui.edisi.detail.ArticleItem

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArtikelScreen(onClickArtikel: (artikelId: Int) -> Unit) {
    val viewModel: ArtikelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val pages = remember {
        listOf(
            Page(R.string.transkrip, TRANSKIP),
            Page(R.string.artikel, ARTIKEL),
            Page(R.string.renungan, RENUNGAN),
            Page(R.string.resensi, RESENSI),
            Page(R.string.lain_lain, LAIN_LAIN)
        )
    }
    Scaffold {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        fun onClickTab(index: Int, page: Page, action: (Int) -> Unit) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }
            val isArticlesEmpty = when (page.category) {
                TRANSKIP -> uiState.transkripArticles.isEmpty()
                ARTIKEL -> uiState.artikelArticles.isEmpty()
                RENUNGAN -> uiState.renunganArticles.isEmpty()
                RESENSI -> uiState.resensiArticles.isEmpty()
                LAIN_LAIN -> uiState.lainLainArticles.isEmpty()
                else -> false
            }
            if (isArticlesEmpty) {
                action(page.category)
            }
        }

        Column(modifier = Modifier.background(PillarColor.background)) {
            ScrollableTabRow(
                containerColor = primary,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions), color = surface)
                }
            ) {
                pages.forEachIndexed { index, page ->
                    val color = if (index == pagerState.currentPage) surface else secondaryVar
                    Tab(
                        text = { Text(color = color, text = stringResource(id = page.label)) },
                        selected = pagerState.currentPage == index,
                        onClick = { onClickTab(index, page) { viewModel.loadArticlesByCategory(it) } }
                    )
                }
            }
            PageContent(pages, uiState, pagerState) { onClickArtikel(it) }
        }
    }
}

@Preview
@Composable
private fun ArtikelScreenPreview() {
    ArtikelScreen {}
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) =
    if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PageContent(pages: List<Page>, uiState: ArtikelViewModelState, pagerState: PagerState, onClick: (artikelId: Int) -> Unit) {

    HorizontalPager(
        modifier = Modifier.disabledHorizontalPointerInputScroll(),
        count = pages.size,
        state = pagerState
    ) { page ->
        val categoryId = pages[page].category

        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            LazyColumn(Modifier.fillMaxSize()) {
                val articlesUi = when (categoryId) {
                    TRANSKIP -> uiState.transkripArticles
                    ARTIKEL -> uiState.artikelArticles
                    RENUNGAN -> uiState.renunganArticles
                    RESENSI -> uiState.resensiArticles
                    else -> uiState.lainLainArticles
                }

                articlesUi.forEachIndexed { index, articleUi ->
                    val isLast = index == articlesUi.size - 1
                    item {
                        ArticleItem(articleUi = articleUi, isLast = isLast) {
                            onClick(articleUi.id)
                        }
                    }
                }
            }
        }
    }
}
