package org.redaksi.ui.utama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.redaksi.data.remote.PillarApi
import javax.inject.Inject

@HiltViewModel
class UtamaViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {

    private val viewModelState = MutableStateFlow(UtamaViewModelState())
    val uiState = viewModelState

    init {
        loadUtama()
    }

    fun loadUtama() {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val newestResult = withContext(Dispatchers.Default) { runCatching { pillarApi.newestArticles() } }
            val newestResponse = newestResult.getOrNull()?.body()
            val newestArticles: List<ArticleUi> = when {
                newestResult.isSuccess && newestResponse != null -> {
                    fromResponse(newestResponse)
                }
                else -> listOf()
            }

            val editorChoiceResult = withContext(Dispatchers.Default) { runCatching { pillarApi.editorChoicesArticles() } }
            val editorChoiceResponse = editorChoiceResult.getOrNull()?.body()
            val editorChoiceArticles: List<ArticleUi> = when {
                editorChoiceResult.isSuccess && editorChoiceResponse != null -> {
                    fromResponse(editorChoiceResponse)
                }
                else -> listOf()
            }

            viewModelState.update { it.copy(newestArticles = newestArticles, editorChoiceArticles = editorChoiceArticles, isLoading = false) }
        }
    }
}

data class UtamaViewModelState(
    val newestArticles: List<ArticleUi> = listOf(),
    val editorChoiceArticles: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)
