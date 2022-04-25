package org.redaksi.ui.komentar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import org.redaksi.ui.ScreenState
import javax.inject.Inject

@HiltViewModel
class KomentarViewModel @Inject constructor(private val pillarApi: PillarApi, savedStateHandle: SavedStateHandle) : ViewModel() {
    private val viewModelState = MutableStateFlow(KomentarViewModelState())
    val uiState = viewModelState
    var artikelId: Int? = null
    init {
        artikelId = savedStateHandle["artikelId"]
        artikelId?.let { loadComments(it) }
    }

    private fun loadComments(artikelId: Int) {
        viewModelScope.launch {
            val result = runCatching { pillarApi.commentByArticle(artikelId) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val commentsUi = fromResponse(response)
                    val screenState = if (commentsUi.isEmpty()) ScreenState.EMPTY else ScreenState.CONTENT
                    viewModelState.update { it.copy(komentarUiList = commentsUi, screenState = screenState) }
                }
            }
        }
    }
}

data class KomentarViewModelState(
    val komentarUiList: List<KomentarUi> = listOf(),
    val screenState: ScreenState = ScreenState.LOADING
)
