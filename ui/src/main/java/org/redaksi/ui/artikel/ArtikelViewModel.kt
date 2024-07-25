package org.redaksi.ui.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.ALKITAB_N_THEOLOGI
import org.redaksi.data.remote.CategoryId
import org.redaksi.data.remote.IMAN_KRISTEN
import org.redaksi.data.remote.ISU_TERKINI
import org.redaksi.data.remote.KEHIDUPAN_KRISTEN
import org.redaksi.data.remote.PillarApi
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.SENI_BUDAYA
import org.redaksi.data.remote.SEPUTAR_GRII
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.artikel.paging.ArtikelSource
import org.redaksi.ui.utama.ArticleUi

@HiltViewModel
class ArtikelViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(ArtikelViewModelState())
    val uiState = viewModelState

    init {
        loadArticlesByCategory(TRANSKIP)
    }

    fun onEvent(artikelEvent: ArtikelEvent) {
        when (artikelEvent) {
            is ArtikelEvent.LoadArticlesByCategory -> loadArticlesByCategory(artikelEvent.categoryId)
        }
    }

    private fun loadArticlesByCategory(@CategoryId categoryId: Int) {
        viewModelScope.launch {
            val artikelPager = Pager(PagingConfig(pageSize = 10)) {
                ArtikelSource(pillarApi, categoryId)
            }.flow.cachedIn(viewModelScope)

            when (categoryId) {
                TRANSKIP -> {
                    viewModelState.update { it.copy(transkripArticles = artikelPager, transkripLoaded = true) }
                }
                ALKITAB_N_THEOLOGI -> {
                    viewModelState.update { it.copy(alkitabTheologiArticles = artikelPager, alkitabTheologiLoaded = true) }
                }
                IMAN_KRISTEN -> {
                    viewModelState.update { it.copy(imanKristenArticles = artikelPager, imanKristenLoaded = true) }
                }
                KEHIDUPAN_KRISTEN -> {
                    viewModelState.update { it.copy(kehidupanKristenArticles = artikelPager, kehidupanKristenLoaded = true) }
                }
                RENUNGAN -> {
                    viewModelState.update { it.copy(renunganArticles = artikelPager, renunganLoaded = true) }
                }
                ISU_TERKINI -> {
                    viewModelState.update { it.copy(isuTerkiniArticles = artikelPager, isuTerkiniLoaded = true) }
                }
                SENI_BUDAYA -> {
                    viewModelState.update { it.copy(seniBudayaArticles = artikelPager, seniBudayaLoaded = true) }
                }
                SEPUTAR_GRII -> {
                    viewModelState.update { it.copy(seputarGriiArticles = artikelPager, seputarGriiLoaded = true) }
                }
                else -> {
                    viewModelState.update { it.copy(resensiArticles = artikelPager, resensiLoaded = true) }
                }
            }
        }
    }
}

data class ArtikelViewModelState(
    val transkripArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val transkripLoaded: Boolean = false,
    val alkitabTheologiArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val alkitabTheologiLoaded: Boolean = false,
    val imanKristenArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val imanKristenLoaded: Boolean = false,
    val kehidupanKristenArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val kehidupanKristenLoaded: Boolean = false,
    val renunganArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val renunganLoaded: Boolean = false,
    val isuTerkiniArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val isuTerkiniLoaded: Boolean = false,
    val seniBudayaArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val seniBudayaLoaded: Boolean = false,
    val seputarGriiArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val seputarGriiLoaded: Boolean = false,
    val resensiArticles: Flow<PagingData<ArticleUi>> = flowOf(),
    val resensiLoaded: Boolean = false
)

sealed class ArtikelEvent {
    data class LoadArticlesByCategory(@CategoryId val categoryId: Int) : ArtikelEvent()
}
