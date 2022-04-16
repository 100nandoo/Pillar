package org.redaksi.ui.artikel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import javax.inject.Inject

@HiltViewModel
class ArtikelDetailViewModel @Inject constructor(private val pillarApi: PillarApi, savedStateHandle: SavedStateHandle) : ViewModel() {
    private val viewModelState = MutableStateFlow(ArtikelDetailViewModelState())
    val uiState = viewModelState
    var artikelId: Int? = null
    init {
        artikelId = savedStateHandle["artikelId"]
        artikelId?.let { loadArtikelDetail(it) }
    }

    private fun loadArtikelDetail(artikelId: Int) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching { pillarApi.articleDetail(artikelId) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val issuesUi = fromResponse(response)
                    viewModelState.update { it.copy(articleDetailUi = issuesUi, isLoading = false) }
                }
            }
        }
    }
}

data class ArtikelDetailViewModelState(
    val articleDetailUi: ArtikelDetailUi = ArtikelDetailUi(),
    val isLoading: Boolean = true
)
