package org.redaksi.ui.komentar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.Dimens
import org.redaksi.ui.Dimens.four
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarColor.background
import org.redaksi.ui.PillarColor.komentarBody
import org.redaksi.ui.PillarTypography
import org.redaksi.ui.R
import org.redaksi.ui.edisi.detail.detailScreenDate
import java.util.Date

@Composable
fun KomentarScreen() {
    val viewModel: KomentarViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold {
        if (uiState.isLoading) {
            LoadingScreen()
        } else {
            LazyColumn {
                uiState.komentarUiList.forEach { komentarUi ->
                    item {
                        KomentarItem(komentarUi)
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun KomentarScreenPreview() {
    KomentarScreen()
}


@Composable
fun KomentarItem(komentarUi: KomentarUi) {
    val paddingTop = Modifier.padding(0.dp, Dimens.eight.dp, 0.dp, 0.dp)
    Column(modifier = Modifier.background(background).padding(sixteen.dp, 0.dp)) {
        Text(modifier = paddingTop, style = PillarTypography.bodyMedium, text = komentarUi.body, color = komentarBody)
        Row(modifier = Modifier.padding(0.dp, four.dp)) {
            Text(
                style = PillarTypography.labelSmall,
                color = PillarColor.edisiDetailBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic,
                text = komentarUi.author
            )
            Text(
                style = PillarTypography.labelSmall,
                color = PillarColor.edisiDetailBody,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text =   " - " + komentarUi.city
            )
            Spacer(Modifier.weight(1f))
            Text(
                style = PillarTypography.labelSmall,
                color = PillarColor.edisiDetailBody,
                text = detailScreenDate(LocalContext.current, komentarUi.date)
            )
        }
        Divider(modifier = paddingTop, color = PillarColor.secondaryVar)
    }
}

@Preview(showBackground = true)
@Composable
private fun KomentarItemPreview() {
    KomentarItem(KomentarUi("Anak-anak Tuhan harus melaksanakan hal pacaran secara Kristen, " +
        "biar Nama Tuhan juga dipermuliakan di muka bumi ini. Gbu", "Budi Wijaya", "Jambi", Date()))
}
