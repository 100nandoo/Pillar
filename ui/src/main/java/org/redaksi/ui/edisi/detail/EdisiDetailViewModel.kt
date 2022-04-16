package org.redaksi.ui.edisi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import javax.inject.Inject

@HiltViewModel
class EdisiDetailViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {
    private val viewModelState = MutableStateFlow(EdisiDetailViewModelState())
    val uiState = viewModelState

    fun loadArticles(issueNumber: String) {
        viewModelScope.launch {
            viewModelState.value = EdisiDetailViewModelState(isLoading = true)
            val result = runCatching { pillarApi.articlesByIssueNumber(issueNumber) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val issuesUi = fromResponse(response)
                    viewModelState.update { it.copy(articlesUi = issuesUi, isLoading = false) }
                }
            }
        }
    }
}

data class EdisiDetailViewModelState(
    val articlesUi: List<ArticleUi> = listOf(),
    val isLoading: Boolean = true
)
