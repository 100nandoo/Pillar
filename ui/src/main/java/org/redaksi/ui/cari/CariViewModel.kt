package org.redaksi.ui.cari

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import org.redaksi.ui.ScreenState
import org.redaksi.ui.cari.paging.CariSource
import org.redaksi.ui.utama.ArticleUi
import javax.inject.Inject

@HiltViewModel
class CariViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(CariViewModelState())
    val uiState = viewModelState

    fun onEvent(cariEvent: CariEvent) {
        when (cariEvent) {
            is CariEvent.LoadSearchArticle -> loadSearchArticle(cariEvent.keyword)
            is CariEvent.UpdateTextFieldValue -> updateTextFieldValue(cariEvent.textFieldValue)
        }
    }

    private fun loadSearchArticle(keyword: String) {
        viewModelScope.launch {
            val cariPager = Pager(PagingConfig(pageSize = 10)) {
                CariSource(pillarApi, keyword)
            }.flow.cachedIn(viewModelScope)
            viewModelState.update { it.copy(articlesUi = cariPager, screenState = ScreenState.CONTENT) }
        }
    }

    private fun updateTextFieldValue(textFieldValue: TextFieldValue) {
        viewModelState.update { it.copy(textFieldValue = textFieldValue) }
    }
}

data class CariViewModelState(
    val articlesUi: Flow<PagingData<ArticleUi>> = flowOf(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val screenState: ScreenState = ScreenState.BLANK
)

sealed class CariEvent {
    data class LoadSearchArticle(val keyword: String) : CariEvent()
    data class UpdateTextFieldValue(val textFieldValue: TextFieldValue) : CariEvent()
}
