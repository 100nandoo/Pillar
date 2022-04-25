package org.redaksi.ui.tanggapan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.ui.EmptyScreen
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.R
import org.redaksi.ui.ScreenState
import org.redaksi.ui.komentar.KomentarItem

@Composable
fun TanggapanScreen(paddingValues: PaddingValues, onClick: (artikelId: Int) -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(PillarColor.background)
            .padding(paddingValues)
    ) {
        val viewModel: TanggapanViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        val listState = rememberLazyListState()

        when (uiState.screenState) {
            ScreenState.LOADING -> LoadingScreen()
            ScreenState.CONTENT -> {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(it)
                        .background(PillarColor.background)
                        .fillMaxSize()
                ) {
                    uiState.komentarUiList.forEach { komentarUi ->
                        item {
                            KomentarItem(komentarUi, true, onClick = { onClick(it) })
                        }
                    }
                }
            }
            else -> EmptyScreen(stringResource(id = R.string.belum_ada_komentar))
        }
    }
}
