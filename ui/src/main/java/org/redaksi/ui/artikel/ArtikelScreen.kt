package org.redaksi.ui.artikel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.redaksi.data.remote.ALKITAB_THEOLOGI
import org.redaksi.data.remote.IMAN_KRISTEN
import org.redaksi.data.remote.ISU_TERKINI
import org.redaksi.data.remote.KEHIDUPAN_KRISTEN
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.SENI_BUDAYA
import org.redaksi.data.remote.SEPUTAR_GRII
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.Dimens
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.secondaryVar
import org.redaksi.ui.PillarColor.surface
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R
import org.redaksi.ui.utama.ArticleUi
import org.redaksi.ui.utama.detailScreenDate
import org.threeten.bp.ZonedDateTime

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArtikelScreen(paddingValues: PaddingValues, onClickArtikel: (artikelId: Int) -> Unit) {
    val viewModel: ArtikelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val pages = remember {
        listOf(
            Page(R.string.transkrip, TRANSKIP),
            Page(R.string.alkitab_theologi, ALKITAB_THEOLOGI),
            Page(R.string.iman_kristen_pekerjaan, IMAN_KRISTEN),
            Page(R.string.kehidupan_kristen, KEHIDUPAN_KRISTEN),
            Page(R.string.renungan, RENUNGAN),
            Page(R.string.isu_terkini, ISU_TERKINI),
            Page(R.string.seni_budaya, SENI_BUDAYA),
            Page(R.string.seputar_grii, SEPUTAR_GRII),
            Page(R.string.resensi, RESENSI)
        )
    }
    Scaffold { it ->
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        fun onClickTab(index: Int, page: Page, action: (Int) -> Unit) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }
            val isArticlesEmpty = when (page.category) {
                TRANSKIP -> uiState.transkripArticles.isEmpty()
                ALKITAB_THEOLOGI -> uiState.alkitabTheologiArticles.isEmpty()
                IMAN_KRISTEN -> uiState.imanKristenArticles.isEmpty()
                KEHIDUPAN_KRISTEN -> uiState.kehidupanKristenArticles.isEmpty()
                RENUNGAN -> uiState.renunganArticles.isEmpty()
                ISU_TERKINI -> uiState.isuTerkiniArticles.isEmpty()
                SENI_BUDAYA -> uiState.seniBudayaArticles.isEmpty()
                SEPUTAR_GRII -> uiState.seputarGriiArticles.isEmpty()
                RESENSI -> uiState.resensiArticles.isEmpty()
                else -> false
            }
            if (isArticlesEmpty) {
                action(page.category)
            }
        }

        Column(
            modifier = Modifier
                .background(PillarColor.background)
                .padding(paddingValues)
                .padding(it)
        ) {
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
    ArtikelScreen(PaddingValues()) {}
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
                    ALKITAB_THEOLOGI -> uiState.alkitabTheologiArticles
                    IMAN_KRISTEN -> uiState.imanKristenArticles
                    KEHIDUPAN_KRISTEN -> uiState.kehidupanKristenArticles
                    RENUNGAN -> uiState.renunganArticles
                    ISU_TERKINI -> uiState.isuTerkiniArticles
                    SENI_BUDAYA -> uiState.seniBudayaArticles
                    SEPUTAR_GRII -> uiState.seputarGriiArticles
                    else -> uiState.resensiArticles
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

@Composable
fun ArticleItem(modifier: Modifier = Modifier, articleUi: ArticleUi, isLast: Boolean, onClick: (artikelId: Int) -> Unit) {
    val paddingTop = modifier.padding(0.dp, Dimens.eight.dp, 0.dp, 0.dp)
    Column(
        modifier
            .padding(Dimens.sixteen.dp, Dimens.eight.dp, Dimens.sixteen.dp, 0.dp)
            .clickable { onClick(articleUi.id) }
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            style = PillarTypography3.headlineSmall,
            color = PillarColor.utamaTitle,
            text = articleUi.title
        )
        if (articleUi.body.isNotBlank()) {
            Text(
                modifier = paddingTop,
                style = PillarTypography3.bodyMedium,
                color = PillarColor.utamaBody,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                text = articleUi.body
            )
        }
        Row(modifier = modifier.padding(0.dp, Dimens.eight.dp)) {
            if (articleUi.authors.isNotBlank()) {
                androidx.compose.material3.Text(
                    modifier = Modifier
                        .weight(1f),
                    style = PillarTypography3.labelSmall,
                    color = PillarColor.utamaBody,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = stringResource(id = R.string.oleh) + " " + articleUi.authors
                )
            }
            if (articleUi.zonedDateTime != null) {
                androidx.compose.material3.Text(
                    style = PillarTypography3.labelSmall,
                    color = PillarColor.utamaBody,
                    text = articleUi.zonedDateTime?.let { detailScreenDate(it) } ?: ""
                )
            }
        }
        if (isLast.not()) {
            Divider(modifier = modifier, color = secondaryVar)
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
            ZonedDateTime.now()
        ),
        false
    ) {}
}

@Preview(showBackground = true)
@Composable
fun ArticleItemLoadingPreview() {
    ArticleItem(
        Modifier,
        ArticleUi(
            0,
            "Doktrin Wahyu: Sebuah Introduksi",
            "Bab pertama buku ini dimulai dengan penjelasan tentang aksiologi (teori nilai) dan hubungan nyata",
            "John Doe",
            ZonedDateTime.now()
        ),
        false
    ) {}
}
