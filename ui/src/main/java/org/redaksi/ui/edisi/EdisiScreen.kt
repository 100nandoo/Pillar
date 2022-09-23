package org.redaksi.ui.edisi

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.Dimens.twelve
import org.redaksi.ui.LoadingScreen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography3
import org.redaksi.ui.R
import org.redaksi.ui.Symbol

@Composable
fun EdisiScreen(
    onClick: (issueNumber: String) -> Unit
) {
    Scaffold { it ->
        val viewModel: EdisiViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        SwipeRefresh(
            modifier = Modifier.padding(it),
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.loadEdisi() }
        ) {
            if (uiState.isLoading) {
                LoadingScreen(false)
            } else {
                LazyColumn {
                    uiState.issuesUi.forEachIndexed { index, issueWithArticle ->
                        if (index == 0) {
                            item {
                                HeaderItem(Modifier.background(PillarColor.background), R.string.artikel_terbaru)
                            }
                            item {
                            }
                            item {
                                HeaderItem(Modifier.background(PillarColor.background), R.string.pilihan_editor)
                            }
                        } else {
                            item {
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EdisiScreenPreview() {
    EdisiScreen {}
}

@Composable
fun HeaderItem(modifier: Modifier, @StringRes id: Int) {
    val staticModifier = modifier
        .background(PillarColor.background)
        .padding(sixteen.dp, sixteen.dp, sixteen.dp, 0.dp)
        .fillMaxWidth()
    Text(modifier = staticModifier, style = PillarTypography3.titleLarge, text = stringResource(id = id))
}

@Preview(
    name = "HeaderItem",
    showSystemUi = true
)
@Composable
private fun HeaderItemPreview() {
    Column {
        HeaderItem(Modifier, R.string.artikel_terbaru)
        HeaderItem(Modifier, R.string.pilihan_editor)

        HeaderItem(Modifier, R.string.artikel_terbaru)
        HeaderItem(Modifier, R.string.pilihan_editor)
    }
}
