package org.redaksi.ui.tanggapan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import org.redaksi.ui.ScreenState
import org.redaksi.ui.komentar.KomentarUi
import org.redaksi.ui.komentar.fromResponse
import javax.inject.Inject

@HiltViewModel
class TanggapanViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(TanggapanViewModelState())
    val uiState = viewModelState

    init {
        loadTanggapan()
    }

    private fun loadTanggapan() {
        viewModelScope.launch {
            viewModelState.update { it.copy(screenState = ScreenState.LOADING) }
            val result = runCatching { pillarApi.latestComments() }
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

data class TanggapanViewModelState(
    val komentarUiList: List<KomentarUi> = listOf(),
    val screenState: ScreenState = ScreenState.LOADING
)
