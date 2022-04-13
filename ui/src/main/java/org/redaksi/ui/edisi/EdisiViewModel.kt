package org.redaksi.ui.edisi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.redaksi.data.remote.PillarApi
import org.redaksi.data.remote.response.base.Issue
import org.redaksi.ui.model.IssueWithArticle
import org.redaksi.ui.model.fromResponse
import javax.inject.Inject

@HiltViewModel
class EdisiViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {

    init {
        loadEdisi()
    }

    private val viewModelState = MutableStateFlow(EdisiViewModelState())

    val uiState = viewModelState

    private fun loadEdisi() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.Default) { runCatching { pillarApi.issues() } }
            val lastIssueResult = withContext(Dispatchers.Default) { pillarApi.lastIssue() }
            when {
                result.isSuccess && lastIssueResult.isSuccessful -> {
                    val issues = result.getOrNull()?.body()?.issues?.items?.toSet() ?: setOf()
                    val body = lastIssueResult.body() ?: return@launch
                    val lastIssue = fromResponse(body)
                    viewModelState.update { it.copy(lastIssue = lastIssue, issues = issues) }
                }
            }
        }
    }
}

data class EdisiViewModelState(
    val lastIssue: IssueWithArticle = IssueWithArticle("", "", "", listOf()),
    val issues: Set<Issue> = setOf()
)
