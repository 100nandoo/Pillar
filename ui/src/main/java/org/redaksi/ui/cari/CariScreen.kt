package org.redaksi.ui.cari

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.EmptyScreen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor.background
import org.redaksi.ui.PillarColor.cariPlaceholder
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.R
import org.redaksi.ui.ScreenState
import org.redaksi.ui.edisi.detail.ArticleItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CariScreen(paddingValues: PaddingValues, onClick: (artikelId: Int) -> Unit) {
    val viewModel: CariViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold { it
        Column(
            Modifier
                .fillMaxSize()
                .background(background)
                .padding(it)
                .padding(paddingValues)
        ) {
            CariTextField(
                uiState = uiState,
                onValueChange = {
                    viewModel.updateTextFieldValue(it)
                },
                onSearch = {
                    if (it.isNotBlank()) {
                        viewModel.loadSearchArticle(it)
                        keyboardController?.hide()
                    }
                }
            )

            when (uiState.screenState) {
                ScreenState.LOADING -> LoadingScreen()
                ScreenState.CONTENT -> {
                    LazyColumn {
                        uiState.articlesUi.forEachIndexed { index, articleUi ->
                            item {
                                val isLast = index == uiState.articlesUi.size - 1
                                ArticleItem(articleUi = articleUi, isLast = isLast) { onClick(articleUi.id) }
                            }
                        }
                    }
                }
                ScreenState.EMPTY -> {
                    EmptyScreen(message = stringResource(id = R.string.tidak_ada_hasil))
                }
            }
        }
    }
}

class AlphaNumericVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val result = text.replace(Regex("[^A-Za-z0-9 ]"), "")
        val offset = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset - (text.length - result.length)
            override fun transformedToOriginal(offset: Int): Int = offset
        }
        return TransformedText(
            text = AnnotatedString(result),
            offsetMapping = offset
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CariTextField(uiState: CariViewModelState, onValueChange: (TextFieldValue) -> Unit, onSearch: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
            .fillMaxWidth(),
        value = uiState.textFieldValue,
        placeholder = { Placeholder() },
        trailingIcon = {
            TrailingIcon(uiState.textFieldValue.text) {
                onSearch(it)
            }
        },
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions(uiState.textFieldValue.text) {
            onSearch(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = primary,
            containerColor = background
        ),
        maxLines = 1,
        visualTransformation = AlphaNumericVisualTransformation()
    )
}

private val keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, true, KeyboardType.Text, ImeAction.Search)
private fun keyboardActions(query: String, onSearch: (String) -> Unit) = KeyboardActions(onSearch = { onSearch(query) })

@Composable
fun TrailingIcon(query: String, onSearch: (String) -> Unit) {
    Icon(
        modifier = Modifier
            .clickable { onSearch(query) }
            .padding(16.dp),
        painter = painterResource(id = R.drawable.ic_cari),
        contentDescription = stringResource(R.string.cari),
        tint = primary
    )
}

@Composable
fun Placeholder() {
    Text(stringResource(R.string.masukan_kata_kunci), color = cariPlaceholder)
}

@Preview
@Composable
private fun CariScreenPreview() {
    CariScreen(PaddingValues()) {}
}
