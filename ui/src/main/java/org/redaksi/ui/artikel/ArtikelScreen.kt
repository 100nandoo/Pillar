package org.redaksi.ui.artikel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
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
fun ArtikelScreen() {
    val viewModel: ArtikelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val pages = remember {
        listOf(
            Page(R.string.transkrip, TRANSKIP), Page(R.string.artikel, ARTIKEL), Page(R.string.renungan, RENUNGAN),
            Page(R.string.resensi, RESENSI), Page(R.string.lain_lain, LAIN_LAIN)
        )
    }
    Scaffold {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        Column(modifier= Modifier.background(PillarColor.background)) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
                }
            ) {
                pages.forEachIndexed { index, page ->
                    Tab(
                        selectedContentColor = surface,
                        unselectedContentColor = secondaryVar,
                        text = { Text(stringResource(id = page.label)) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                            when (page.category) {
                                TRANSKIP -> {
                                    if (uiState.transkripArticles.isEmpty()) {
                                        viewModel.loadArticlesByCategory(page.category)
                                    }
                                }
                                ARTIKEL -> {
                                    if (uiState.artikelArticles.isEmpty()) {
                                        viewModel.loadArticlesByCategory(page.category)
                                    }
                                }
                                RENUNGAN -> {
                                    if (uiState.renunganArticles.isEmpty()) {
                                        viewModel.loadArticlesByCategory(page.category)
                                    }
                                }
                                RESENSI -> {
                                    if (uiState.resensiArticles.isEmpty()) {
                                        viewModel.loadArticlesByCategory(page.category)
                                    }
                                }
                                LAIN_LAIN -> {
                                    if (uiState.lainLainArticles.isEmpty()) {
                                        viewModel.loadArticlesByCategory(page.category)
                                    }
                                }
                            }
                        },
                    )
                }
            }


            HorizontalPager(
                count = pages.size,
                state = pagerState,
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
                                    // onClick(articleUi.id)
                                }
                            }

                        }
                    }
                }
            }
        }

    }
}
@Preview
@Composable
private fun ArtikelScreenPreview() {
    ArtikelScreen()
}
