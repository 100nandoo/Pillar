package org.redaksi.ui.komentar

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
class KomentarViewModel @Inject constructor(private val pillarApi: PillarApi,  savedStateHandle: SavedStateHandle): ViewModel() {
    private val viewModelState = MutableStateFlow(KomentarViewModelState())
    val uiState = viewModelState

    init {
        val artikelId: Int? = savedStateHandle["artikelId"]
        artikelId?.let { loadComments(it) }
    }

    private fun loadComments(artikelId: Int) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching { pillarApi.commentByArticle(artikelId) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val commentsUi = fromResponse(response)
                    viewModelState.update { it.copy(komentarUiList = commentsUi, isLoading = false) }
                }
            }
        }
    }
}

data class KomentarViewModelState(
    val komentarUiList: List<KomentarUi> = listOf(),
    val isLoading: Boolean = true
)
