package org.redaksi.ui.artikel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.ARTIKEL
import org.redaksi.data.remote.CategoryId
import org.redaksi.data.remote.PillarApi
import org.redaksi.data.remote.RENUNGAN
import org.redaksi.data.remote.RESENSI
import org.redaksi.data.remote.TRANSKIP
import org.redaksi.ui.edisi.detail.ArticleUi
import org.redaksi.ui.edisi.detail.fromResponse
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
                    val issuesUi = fromResponse(response)
                    when (categoryId) {
                        TRANSKIP -> { viewModelState.update { it.copy(transkripArticles = issuesUi, isLoading = false) } }
                        ARTIKEL -> { viewModelState.update { it.copy(artikelArticles = issuesUi, isLoading = false) } }
                        RENUNGAN -> { viewModelState.update { it.copy(renunganArticles = issuesUi, isLoading = false) } }
                        RESENSI -> { viewModelState.update { it.copy(resensiArticles = issuesUi, isLoading = false) } }
                        else -> { viewModelState.update { it.copy(lainLainArticles = issuesUi, isLoading = false) } }
                    }
                }
            }
        }
    }
}

data class ArtikelViewModelState(
    val transkripArticles: List<ArticleUi> = listOf(),
    val artikelArticles: List<ArticleUi> = listOf(),
    val renunganArticles: List<ArticleUi> = listOf(),
    val resensiArticles: List<ArticleUi> = listOf(),
    val lainLainArticles: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)
