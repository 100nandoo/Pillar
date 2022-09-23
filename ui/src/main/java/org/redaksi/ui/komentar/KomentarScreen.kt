package org.redaksi.ui.komentar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens
import org.redaksi.ui.Dimens.four
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.Dimens.thirtyTwo
import org.redaksi.ui.EmptyScreen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.background
import org.redaksi.ui.PillarColor.komentarBody
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.secondaryVar
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R
import org.redaksi.ui.ScreenState
import org.redaksi.ui.utama.detailScreenDate
import org.threeten.bp.ZonedDateTime

@Composable
fun KomentarScreen(
    onClickBuatKomentar: (Int) -> Unit
) {
    val viewModel: KomentarViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    Scaffold(
        bottomBar = {
            BottomAppBar(cutoutShape = CircleShape, content = {}, backgroundColor = primary)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.artikelId?.let { onClickBuatKomentar(it) }
                },
                shape = CircleShape,
                containerColor = secondaryVar
            ) {
                Icon(imageVector = Icons.Rounded.Create, contentDescription = stringResource(R.string.buat_komentar), tint = primary)
            }
        },
        isFloatingActionButtonDocked = true
    ) { paddingValue ->
        when (uiState.screenState) {
            ScreenState.LOADING -> LoadingScreen()
            ScreenState.CONTENT -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(paddingValue)
                        .background(background)
                        .fillMaxSize()
                ) {
                    uiState.komentarUiList.forEachIndexed { index, komentarUi ->
                        item {
                            KomentarItem(komentarUi, false) {}
                        }
                        if (index == uiState.komentarUiList.size - 1) {
                            item {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(thirtyTwo.dp)
                                )
                            }
                        }
                    }
                }
            }
            ScreenState.EMPTY -> EmptyScreen(stringResource(id = R.string.belum_ada_komentar))
            else -> EmptyScreen(stringResource(id = R.string.belum_ada_komentar))
        }
    }
}

@Preview
@Composable
private fun KomentarScreenPreview() {
    KomentarScreen {}
}

@Composable
fun KomentarItem(komentarUi: KomentarUi, isClickable: Boolean, onClick: (artikelId: Int) -> Unit) {
    val paddingTop = Modifier.padding(0.dp, Dimens.eight.dp, 0.dp, 0.dp)
    Column(
        modifier = Modifier
            .background(background)
            .padding(sixteen.dp, 0.dp)
            .clickable(isClickable) { onClick(komentarUi.articleId) }
    ) {
        Text(modifier = paddingTop, style = PillarTypography3.bodyMedium, text = komentarUi.body, color = komentarBody)
        Row(modifier = Modifier.padding(0.dp, four.dp)) {
            Text(
                style = PillarTypography3.labelSmall,
                color = PillarColor.utamaBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic,
                text = komentarUi.author
            )
            Text(
                style = PillarTypography3.labelSmall,
                color = PillarColor.utamaBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = " - " + komentarUi.city
            )
            Spacer(Modifier.weight(1f))
            Text(
                style = PillarTypography3.labelSmall,
                color = PillarColor.utamaBody,
                text = detailScreenDate(komentarUi.zonedDateTime)
            )
        }
        Divider(modifier = paddingTop, color = secondaryVar)
    }
}

@Preview(showBackground = true)
@Composable
private fun KomentarItemPreview() {
    KomentarItem(
        KomentarUi(
            "Anak-anak Tuhan harus melaksanakan hal pacaran secara Kristen, " +
                "biar Nama Tuhan juga dipermuliakan di muka bumi ini. Gbu",
            "Budi Wijaya",
            "Jambi",
            ZonedDateTime.now(),
            2000
        ),
        false
    ) {}
}
