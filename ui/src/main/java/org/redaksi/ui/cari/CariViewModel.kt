package org.redaksi.ui.cari

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import org.redaksi.ui.ScreenState
import org.redaksi.ui.utama.ArticleUi
import org.redaksi.ui.utama.fromResponse
import javax.inject.Inject

@HiltViewModel
class CariViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(CariViewModelState())
    val uiState = viewModelState

    fun loadSearchArticle(keyword: String) {
        viewModelScope.launch {
            viewModelState.update { it.copy(screenState = ScreenState.LOADING) }
            val result = runCatching { pillarApi.searchArticle(keyword) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val articlesUi = fromResponse(response)
                    val screenState = if (articlesUi.isEmpty()) ScreenState.EMPTY else ScreenState.CONTENT
                    viewModelState.update { it.copy(articlesUi = articlesUi, screenState = screenState) }
                }
            }
        }
    }

    fun updateTextFieldValue(textFieldValue: TextFieldValue) {
        viewModelState.update { it.copy(textFieldValue = textFieldValue) }
    }
}

data class CariViewModelState(
    val articlesUi: List<ArticleUi> = listOf(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val screenState: ScreenState = ScreenState.BLANK
)
