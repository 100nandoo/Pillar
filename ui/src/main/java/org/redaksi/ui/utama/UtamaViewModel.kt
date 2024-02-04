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

    fun onEvent(utamaEvent: UtamaEvent) {
        when (utamaEvent) {
            is UtamaEvent.LoadUtama -> loadUtama()
        }
    }

    private fun loadUtama() {
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

            viewModelState.update { it.copy(highlightArticle = editorChoiceArticles.first(), newestArticles = newestArticles, editorChoiceArticles = editorChoiceArticles.drop(1), isLoading = false) }
        }
    }
}

data class UtamaViewModelState(
    val highlightArticle: ArticleUi = ArticleUi(),
    val newestArticles: List<ArticleUi> = listOf(),
    val editorChoiceArticles: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)

sealed class UtamaEvent {
    object LoadUtama : UtamaEvent()
}
