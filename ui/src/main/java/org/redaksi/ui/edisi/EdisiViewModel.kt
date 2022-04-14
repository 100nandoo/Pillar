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
import javax.inject.Inject

@HiltViewModel
class EdisiViewModel @Inject constructor(private val pillarApi: PillarApi) : ViewModel() {

    private val viewModelState = MutableStateFlow(EdisiViewModelState())
    val uiState = viewModelState

    init {
        loadEdisi()
    }


    fun loadEdisi() {
        viewModelScope.launch {
            viewModelState.value = EdisiViewModelState(isLoading = true)
            val result = withContext(Dispatchers.Default) { runCatching { pillarApi.issues() } }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val issuesUi = fromResponse(response)
                    viewModelState.update { it.copy(issuesUi = issuesUi, isLoading = false) }
                }
            }
        }
    }
}

data class EdisiViewModelState(
    val issuesUi: List<IssueUi> = listOf(),
    val isLoading: Boolean = true
)
