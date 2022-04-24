package org.redaksi.ui.komentar.buat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BuatKomentarScreen(
    onKomentarInserted: () -> Unit
) {
    val viewModel: BuatKomentarViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val arrayBuatTextField: List<BuatTextField> = remember {
        listOf(
            BuatTextField(BuatTextFieldEnum.NAMA, R.string.nama),
            BuatTextField(BuatTextFieldEnum.KOTA, R.string.kota),
            BuatTextField(BuatTextFieldEnum.EMAIL, R.string.email),
            BuatTextField(BuatTextFieldEnum.KOMENTAR, R.string.komentar)
        )
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape, content = {}, backgroundColor = PillarColor.primary)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val artikelId = viewModel.artikelId
                if (artikelId != null) {
                    viewModel.insertComment(artikelId)
                } else {
                    // todo show snack bar
                }
            }, shape = CircleShape, containerColor = PillarColor.secondaryVar) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = stringResource(R.string.buat_komentar),
                    tint = PillarColor.primary
                )
            }

        },
        isFloatingActionButtonDocked = true,
    ) {
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            uiState.pageState == PageState.COMPOSING -> {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .background(PillarColor.background)
                        .fillMaxSize()
                ) {
                    /**
                     * todo Tulis Komentar Header
                     * Text(stringResource(R.string.tulis_komentar), style = PillarTypography3.headlineSmall)
                     */
                    InsertKomentar(
                        paddingValues = it,
                        arrayBuatTextField = arrayBuatTextField,
                        uiState = uiState,
                        onClear = { buatTextField -> viewModel.clearTextField(buatTextField) },
                        onValueChanged = { buatTextField, textFieldValue -> viewModel.onValueChanged(buatTextField, textFieldValue) })
                }
            }
            uiState.pageState == PageState.INSERTED -> {
                onKomentarInserted()
            }
            else -> {
                // todo show snack bar
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InsertKomentar(
    paddingValues: PaddingValues,
    arrayBuatTextField: List<BuatTextField>,
    uiState: BuatKomentarViewModelState,
    onClear: (BuatTextFieldEnum) -> Unit,
    onValueChanged: (BuatTextFieldEnum, TextFieldValue) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(Dimens.sixteen.dp, Dimens.forty.dp, Dimens.sixteen.dp, 0.dp)
    ) {
        val paddingTop = Modifier.padding(0.dp, Dimens.twelve.dp, 0.dp, 0.dp)
        arrayBuatTextField.forEach { buatTextField ->
            val textFieldValue = when (buatTextField.enum) {
                BuatTextFieldEnum.NAMA -> {
                    uiState.namaValue
                }
                BuatTextFieldEnum.KOTA -> {
                    uiState.kotaValue
                }
                BuatTextFieldEnum.EMAIL -> {
                    uiState.emailValue
                }
                else -> {
                    uiState.komentarValue
                }
            }
            val singleLine = buatTextField.enum != BuatTextFieldEnum.KOMENTAR
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            val keyboardActions = if (buatTextField.enum == BuatTextFieldEnum.KOMENTAR) {
                KeyboardActions(onDone = { keyboardController?.hide() })
            } else {
                KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            }

            val keyboardOptions = when (buatTextField.enum) {
                BuatTextFieldEnum.NAMA, BuatTextFieldEnum.KOTA -> KeyboardOptions(KeyboardCapitalization.Sentences, false, KeyboardType.Text, ImeAction.Next)
                BuatTextFieldEnum.EMAIL -> KeyboardOptions(KeyboardCapitalization.Sentences, false, KeyboardType.Email, ImeAction.Next)
                else -> KeyboardOptions(KeyboardCapitalization.Sentences, true, KeyboardType.Text, ImeAction.Done)
            }
            TextField(
                modifier = paddingTop.fillMaxWidth(),
                value = textFieldValue,
                label = { Text(text = stringResource(id = buatTextField.label), style = PillarTypography3.bodyMedium) },
                singleLine = singleLine,
                trailingIcon = {
                    TrailingIconClear(buatTextField.enum) {
                        onClear(it)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = PillarColor.komentarBackground
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = { onValueChanged(buatTextField.enum, it) },
            )
        }
    }
}

@Composable
fun TrailingIconClear(buatTextFieldEnum: BuatTextFieldEnum, onClear: (BuatTextFieldEnum) -> Unit) {
    Icon(
        modifier = Modifier
            .clickable { onClear(buatTextFieldEnum) }
            .padding(16.dp),
        imageVector = Icons.Default.Clear,
        contentDescription = stringResource(R.string.hapus),
        tint = PillarColor.cariPlaceholder
    )
}

@Preview(showBackground = true)
@Composable
private fun InsertKomentarPreview() {
    InsertKomentar(PaddingValues(0.dp), listOf(), BuatKomentarViewModelState(), {}, { _, _ -> })
}
