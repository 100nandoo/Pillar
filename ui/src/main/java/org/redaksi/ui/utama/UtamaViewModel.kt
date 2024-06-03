package org.redaksi.ui.utama

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.redaksi.data.remote.PillarApi
import javax.inject.Inject

@HiltViewModel
class UtamaViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val _uiState = MutableStateFlow(UtamaViewModelState())
    val uiState = _uiState.asStateFlow()

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
            _uiState.update { it.copy(isLoading = true) }
            val newestArticles = fetchNewestArticles()
            val editorChoiceArticles = fetchEditorChoiceArticles()

            _uiState.update {
                it.copy(
                    highlightArticle = if (newestArticles.isNotEmpty()) newestArticles.first() else null,
                    newestArticles = if (newestArticles.isNotEmpty()) newestArticles.drop(1) else emptyList(),
                    editorChoiceArticles = editorChoiceArticles,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun fetchNewestArticles(): List<ArticleUi> {
        return withContext(Dispatchers.IO) {
            runCatching { pillarApi.newestArticles() }
                .getOrNull()
                ?.body()
                ?.let { fromResponse(it) } ?: emptyList()
        }
    }

    private suspend fun fetchEditorChoiceArticles(): List<ArticleUi> {
        return withContext(Dispatchers.IO) {
            runCatching { pillarApi.editorChoicesArticles() }
                .getOrNull()
                ?.body()
                ?.let { fromResponse(it) } ?: emptyList()
        }
    }
}

data class UtamaViewModelState(
    val highlightArticle: ArticleUi? = ArticleUi(),
    val newestArticles: List<ArticleUi> = listOf(),
    val editorChoiceArticles: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)

sealed class UtamaEvent {
    object LoadUtama : UtamaEvent()
}
