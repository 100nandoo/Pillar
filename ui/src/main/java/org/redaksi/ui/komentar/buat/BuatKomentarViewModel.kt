package org.redaksi.ui.komentar.buat

import androidx.annotation.StringRes
import androidx.compose.ui.text.input.TextFieldValue
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
class BuatKomentarViewModel @Inject constructor(private val pillarApi: PillarApi, savedStateHandle: SavedStateHandle) : ViewModel() {
    private val viewModelState = MutableStateFlow(BuatKomentarViewModelState())
    val uiState = viewModelState
    var artikelId: Int? = null

    init {
        artikelId = savedStateHandle["artikelId"]
    }

    fun insertComment(artikelId: Int) {
        viewModelScope.launch {
            viewModelState.update { it.copy(isLoading = true) }
            val result = runCatching {
                pillarApi.insertComment(
                    artikelId,
                    uiState.value.namaValue.text,
                    uiState.value.kotaValue.text,
                    uiState.value.emailValue.text,
                    uiState.value.komentarValue.text
                )
            }
            val response = result.getOrNull()?.body()
            when {
                result.isSuccess && response != null -> {
                    viewModelState.update { it.copy(pageState = PageState.INSERTED, isLoading = false) }
                }
                else -> viewModelState.update { it.copy(pageState = PageState.FAILED, isLoading = false) }

            }
        }
    }

    fun clearTextField(buatTextFieldEnum: BuatTextFieldEnum) {
        when (buatTextFieldEnum) {
            BuatTextFieldEnum.NAMA -> {
                viewModelState.update { it.copy(namaValue = TextFieldValue()) }
            }
            BuatTextFieldEnum.KOTA -> {
                viewModelState.update { it.copy(kotaValue = TextFieldValue()) }
            }
            BuatTextFieldEnum.EMAIL -> {
                viewModelState.update { it.copy(emailValue = TextFieldValue()) }
            }
            else -> {
                viewModelState.update { it.copy(komentarValue = TextFieldValue()) }
            }
        }
    }

    fun onValueChanged(buatTextFieldEnum: BuatTextFieldEnum, textFieldValue: TextFieldValue) {
        when (buatTextFieldEnum) {
            BuatTextFieldEnum.NAMA -> {
                viewModelState.update { it.copy(namaValue = textFieldValue) }
            }
            BuatTextFieldEnum.KOTA -> {
                viewModelState.update { it.copy(kotaValue = textFieldValue) }
            }
            BuatTextFieldEnum.EMAIL -> {
                viewModelState.update { it.copy(emailValue = textFieldValue) }
            }
            else -> {
                viewModelState.update { it.copy(komentarValue = textFieldValue) }
            }
        }
    }
}

data class BuatKomentarViewModelState(
    val pageState: PageState = PageState.COMPOSING,
    val isLoading: Boolean = false,
    val namaValue: TextFieldValue = TextFieldValue(),
    val kotaValue: TextFieldValue = TextFieldValue(),
    val emailValue: TextFieldValue = TextFieldValue(),
    val komentarValue: TextFieldValue = TextFieldValue(),
)

data class BuatTextField(val enum: BuatTextFieldEnum, @StringRes val label: Int)

enum class BuatTextFieldEnum {
    NAMA, KOTA, EMAIL, KOMENTAR
}

enum class PageState {
    COMPOSING, INSERTED, FAILED
}
