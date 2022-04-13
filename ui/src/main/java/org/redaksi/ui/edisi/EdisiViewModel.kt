package org.redaksi.ui.edisi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import org.redaksi.data.remote.response.base.Issue
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
            val result = runCatching { pillarApi.issues() }
            when {
                result.isSuccess -> {
                    val issues = result.getOrNull()?.body()?.issues?.items?.toSet() ?: setOf()
                    viewModelState.update { it.copy(issues = issues) }
                }
            }
        }
    }
}

data class EdisiViewModelState(
    val issues: Set<Issue> = setOf()
)
