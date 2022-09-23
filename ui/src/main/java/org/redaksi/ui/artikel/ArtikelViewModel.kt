package org.redaksi.ui.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.*
import org.redaksi.ui.utama.ArticleUi
import org.redaksi.ui.utama.fromResponse
import javax.inject.Inject

@HiltViewModel
class ArtikelViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(ArtikelViewModelState())
    val uiState = viewModelState

    init {
        loadArticlesByCategory(TRANSKIP)
    }

    fun loadArticlesByCategory(@CategoryId categoryId: Int) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching { pillarApi.articlesByCategory(categoryId) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val articleUis = fromResponse(response)
                    when (categoryId) {
                        TRANSKIP -> { viewModelState.update { it.copy(transkripArticles = articleUis, isLoading = false) } }
                        ALKITAB_N_THEOLOGI -> { viewModelState.update { it.copy(alkitabTheologiArticles = articleUis, isLoading = false) } }
                        IMAN_KRISTEN -> { viewModelState.update { it.copy(imanKristenArticles = articleUis, isLoading = false) } }
                        KEHIDUPAN_KRISTEN -> { viewModelState.update { it.copy(kehidupanKristenArticles = articleUis, isLoading = false) } }
                        RENUNGAN -> { viewModelState.update { it.copy(renunganArticles = articleUis, isLoading = false) } }
                        ISU_TERKINI -> { viewModelState.update { it.copy(isuTerkiniArticles = articleUis, isLoading = false) } }
                        SENI_BUDAYA -> { viewModelState.update { it.copy(seniBudayaArticles = articleUis, isLoading = false) } }
                        SEPUTAR_GRII -> { viewModelState.update { it.copy(seputarGriiArticles = articleUis, isLoading = false) } }
                        else -> { viewModelState.update { it.copy(resensiArticles = articleUis, isLoading = false) } }
                    }
                }
            }
        }
    }
}

data class ArtikelViewModelState(
    val transkripArticles: List<ArticleUi> = listOf(),
    val alkitabTheologiArticles: List<ArticleUi> = listOf(),
    val imanKristenArticles: List<ArticleUi> = listOf(),
    val kehidupanKristenArticles: List<ArticleUi> = listOf(),
    val renunganArticles: List<ArticleUi> = listOf(),
    val isuTerkiniArticles: List<ArticleUi> = listOf(),
    val seniBudayaArticles: List<ArticleUi> = listOf(),
    val seputarGriiArticles: List<ArticleUi> = listOf(),
    val resensiArticles: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)
