package org.redaksi.ui.artikel.detail

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.redaksi.data.remote.PillarApi
import javax.inject.Inject

@HiltViewModel
class ArtikelDetailViewModel @Inject constructor(
    private val pillarApi: PillarApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val viewModelState = MutableStateFlow(ArtikelDetailViewModelState())
    val uiState = viewModelState
    var artikelId: Int? = null
    var slug: String? = null

    init {
        artikelId = savedStateHandle["artikelId"]
        slug = savedStateHandle["slug"]
        artikelId?.let { loadArtikelDetail(it) }
        slug?.let { loadArtikelDetailSlug(it) }
    }

    fun onEvent(event: ArtikelDetailEvent) {
        when (event) {
            is ArtikelDetailEvent.ShowNotKnownDialog -> showNotKnownDialog(event.verse)
            is ArtikelDetailEvent.DismissNotKnownDialog -> dismissNotKnownDialog()
            is ArtikelDetailEvent.ShowKnownDialog -> showKnownDialog(event.isShown, event.verse, event.ari)
            is ArtikelDetailEvent.PlayStoreDialog -> playStoreDialog(event.isShown)
            is ArtikelDetailEvent.InstallDialog -> installDialog(event.isShown)
        }
    }

    private fun loadArtikelDetail(artikelId: Int) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching { pillarApi.articleDetail(artikelId) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val artikelDetailUi = fromResponse(response)
                    viewModelState.update { it.copy(articleDetailUi = artikelDetailUi, isLoading = false) }
                }
            }
        }
    }

    private fun loadArtikelDetailSlug(slug: String) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching { pillarApi.articleDetailSlug(slug) }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    val artikelDetailUi = fromResponse(response[0])
                    viewModelState.update { it.copy(articleDetailUi = artikelDetailUi, isLoading = false) }
                }
            }
        }
    }

    private fun showNotKnownDialog(verse: String) {
        viewModelState.update { it.copy(showNotKnownDialog = Pair(true, verse)) }
    }

    private fun dismissNotKnownDialog() {
        viewModelState.update { it.copy(showNotKnownDialog = Pair(false, "")) }
    }

    private fun showKnownDialog(isShown: Boolean, verse: AnnotatedString = AnnotatedString(""), ari: Int = 0) {
        viewModelState.update { it.copy(showKnownDialog = Triple(isShown, verse, ari)) }
    }

    private fun playStoreDialog(isShown: Boolean) {
        viewModelState.update { it.copy(showPlayStoreDialog = isShown) }
    }

    private fun installDialog(isShown: Boolean) {
        viewModelState.update { it.copy(showAlkitabInstalledDialog = isShown) }
    }
}

data class ArtikelDetailViewModelState(
    val articleDetailUi: ArtikelDetailUi = ArtikelDetailUi(),
    val isLoading: Boolean = true,
    val showNotKnownDialog: Pair<Boolean, String> = Pair(false, ""),
    val showKnownDialog: Triple<Boolean, AnnotatedString, Int> = Triple(false, AnnotatedString(""), 0),
    val showAlkitabInstalledDialog: Boolean = false,
    val showPlayStoreDialog: Boolean = false
)

sealed class ArtikelDetailEvent {
    data class ShowNotKnownDialog(val verse: String) : ArtikelDetailEvent()
    object DismissNotKnownDialog : ArtikelDetailEvent()
    data class ShowKnownDialog(val isShown: Boolean, val verse: AnnotatedString, val ari: Int) : ArtikelDetailEvent()
    data class PlayStoreDialog(val isShown: Boolean) : ArtikelDetailEvent()
    data class InstallDialog(val isShown: Boolean) : ArtikelDetailEvent()
}
