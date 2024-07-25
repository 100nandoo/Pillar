package org.redaksi.ui.artikel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.redaksi.data.remote.ALKITAB_N_THEOLOGI
import org.redaksi.data.remote.IMAN_KRISTEN
import org.redaksi.data.remote.ISU_TERKINI
import org.redaksi.data.remote.KEHIDUPAN_KRISTEN
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.SENI_BUDAYA
import org.redaksi.data.remote.SEPUTAR_GRII
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.Dimens.EIGHT
import org.redaksi.ui.Dimens.SIXTEEN
import org.redaksi.ui.Dimens.THIRTY_TWO
import org.redaksi.ui.Dimens.TWELVE
import org.redaksi.ui.EmptyScreen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.R
import org.redaksi.ui.compose.PillarColor
import org.redaksi.ui.compose.PillarColor.bottomBarSelected
import org.redaksi.ui.compose.PillarColor.primary
import org.redaksi.ui.compose.PillarColor.secondary
import org.redaksi.ui.compose.PillarColor.secondaryVar
import org.redaksi.ui.compose.PillarTypography3
import org.redaksi.ui.compose.UiModelProvider
import org.redaksi.ui.utama.ArticleUi

@Composable
fun ArtikelScreen(paddingValues: PaddingValues, onClickArtikel: (artikelId: Int) -> Unit) {
    val viewModel: ArtikelViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val pages = remember {
        listOf(
            Page(R.string.transkrip, TRANSKIP),
            Page(R.string.alkitab_theologi, ALKITAB_N_THEOLOGI),
            Page(R.string.iman_kristen_pekerjaan, IMAN_KRISTEN),
            Page(R.string.kehidupan_kristen, KEHIDUPAN_KRISTEN),
            Page(R.string.renungan, RENUNGAN),
            Page(R.string.isu_terkini, ISU_TERKINI),
            Page(R.string.seni_budaya, SENI_BUDAYA),
            Page(R.string.seputar_grii, SEPUTAR_GRII),
            Page(R.string.resensi, RESENSI)
        )
    }

    ArtikelScreenContent(
        pages = pages,
        uiState = uiState,
        paddingValues = paddingValues,
        loadCategory = { viewModel.onEvent(ArtikelEvent.LoadArticlesByCategory(it)) },
        onClick = { onClickArtikel(it) }
    )
}

@Preview
@Composable
private fun ArtikelScreenPreview() {
    ArtikelScreenContent(UiModelProvider.pageList, ArtikelViewModelState(), PaddingValues(), {}) {}
}

private val HorizontalScrollConsumer = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource) = available.copy(y = 0f)
    override suspend fun onPreFling(available: Velocity) = available.copy(y = 0f)
}

fun Modifier.disabledHorizontalPointerInputScroll(disabled: Boolean = true) = if (disabled) this.nestedScroll(HorizontalScrollConsumer) else this

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ArtikelScreenContent(
    pages: List<Page>,
    uiState: ArtikelViewModelState,
    paddingValues: PaddingValues,
    loadCategory: (Int) -> Unit,
    onClick: (artikelId: Int) -> Unit
) {
    Scaffold { it ->
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState {
            pages.size
        }

        fun onClickTab(index: Int, page: Page, action: (Int) -> Unit) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(index)
            }

            val isLoaded = when (page.category) {
                TRANSKIP -> uiState.transkripLoaded
                ALKITAB_N_THEOLOGI -> uiState.alkitabTheologiLoaded
                IMAN_KRISTEN -> uiState.imanKristenLoaded
                KEHIDUPAN_KRISTEN -> uiState.kehidupanKristenLoaded
                RENUNGAN -> uiState.renunganLoaded
                ISU_TERKINI -> uiState.isuTerkiniLoaded
                SENI_BUDAYA -> uiState.seniBudayaLoaded
                SEPUTAR_GRII -> uiState.seputarGriiLoaded
                RESENSI -> uiState.resensiLoaded
                else -> false
            }
            if (isLoaded.not()) {
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
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions
                        ),
                        color = secondary
                    )
                }
            ) {
                pages.forEachIndexed { index, page ->
                    val color =
                        if (index == pagerState.currentPage) secondary else bottomBarSelected
                    Tab(
                        text = { Text(color = color, text = stringResource(id = page.label)) },
                        selected = pagerState.currentPage == index,
                        onClick = { onClickTab(index, page) { loadCategory(it) } }
                    )
                }
            }

            HorizontalPager(
                modifier = Modifier.disabledHorizontalPointerInputScroll(),
                state = pagerState
            ) { page ->
                val categoryId = pages[page].category

                val articlesUi = when (categoryId) {
                    TRANSKIP -> uiState.transkripArticles
                    ALKITAB_N_THEOLOGI -> uiState.alkitabTheologiArticles
                    IMAN_KRISTEN -> uiState.imanKristenArticles
                    KEHIDUPAN_KRISTEN -> uiState.kehidupanKristenArticles
                    RENUNGAN -> uiState.renunganArticles
                    ISU_TERKINI -> uiState.isuTerkiniArticles
                    SENI_BUDAYA -> uiState.seniBudayaArticles
                    SEPUTAR_GRII -> uiState.seputarGriiArticles
                    else -> uiState.resensiArticles
                }
                ArtikelList(articles = articlesUi, onClick = { onClick(it) })
            }
        }
    }
}

@Composable
fun ArtikelList(articles: Flow<PagingData<ArticleUi>>, onClick: (artikelId: Int) -> Unit, isSearch: Boolean = false) {
    val articleItems = articles.collectAsLazyPagingItems()

    LazyColumn(Modifier.fillMaxSize()) {
        items(
            count = articleItems.itemCount,
            key = articleItems.itemKey { it.id }
        ) { index ->
            val articleUi = articleItems[index]
            articleUi?.let { articleUi ->
                ArticleItem(articleUi = articleUi) {
                    onClick(articleUi.id)
                }
            }
        }

        articleItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingScreen(Modifier.fillParentMaxSize()) }
                }

                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingScreen(
                            Modifier
                                .fillMaxWidth()
                                .padding(SIXTEEN.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                this.itemCount == 0 && isSearch -> {
                    item {
                        EmptyScreen(message = stringResource(id = R.string.tidak_ada_hasil))
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(modifier: Modifier = Modifier, articleUi: ArticleUi, isDividerShown: Boolean = false, onClick: (artikelId: Int) -> Unit) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(articleUi.id) }
    ) {
        val (image, title, column) = createRefs()
        val isImageExist = articleUi.imageUrl.isNotBlank()
        val cardHorMargin = if (isImageExist) THIRTY_TWO else 0
        val cardBottomMargin = if (isImageExist) SIXTEEN else 0

        if (isImageExist) {
            AsyncImage(
                model = articleUi.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .constrainAs(image) {}
            )
        }
        Text(
            modifier = modifier
                .padding(cardHorMargin.dp, 0.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(SIXTEEN, SIXTEEN))
                .background(PillarColor.background)
                .padding(TWELVE.dp, TWELVE.dp, TWELVE.dp, EIGHT.dp)
                .constrainAs(title) {
                    bottom.linkTo(image.bottom)
                },
            style = PillarTypography3.headlineSmall,
            color = secondary,
            text = articleUi.title
        )
        Column(
            modifier
                .padding(cardHorMargin.dp, 0.dp)
                .background(PillarColor.background)
                .padding(TWELVE.dp, 0.dp, TWELVE.dp, cardBottomMargin.dp)
                .constrainAs(column) {
                    top.linkTo(title.bottom)
                }
        ) {
            if (articleUi.body.isNotBlank()) {
                Text(
                    style = PillarTypography3.bodyMedium,
                    color = PillarColor.utamaBody,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    text = articleUi.body
                )
            }
            Row(modifier = modifier.padding(0.dp, EIGHT.dp)) {
                if (articleUi.authors.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        style = PillarTypography3.labelSmall,
                        color = PillarColor.utamaBody,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = stringResource(id = R.string.oleh) + " " + articleUi.authors
                    )
                }
                if (articleUi.displayDate.isNotBlank()) {
                    Text(
                        style = PillarTypography3.labelSmall,
                        color = PillarColor.utamaBody,
                        text = articleUi.displayDate
                    )
                }
            }
            if (isImageExist.not() || isDividerShown) {
                HorizontalDivider(modifier = modifier, color = secondaryVar)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        Modifier,
        UiModelProvider.articleUiList.first(),
        false
    ) {}
}
