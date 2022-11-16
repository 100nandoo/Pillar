package org.redaksi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.redaksi.ui.compose.PillarColor
import org.redaksi.ui.compose.PillarColor.secondaryVar
import org.redaksi.ui.compose.PillarTypography3

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, isLoading: Boolean = true) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(PillarColor.background)
            .fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = secondaryVar)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Composable
fun EmptyScreen(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(PillarColor.background)
            .fillMaxSize()
    ) {
        Text(
            message,
            style = PillarTypography3.headlineMedium,
            color = PillarColor.artikelDetailTitle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmptyScreenPreview() {
    EmptyScreen(stringResource(id = R.string.tidak_ada_hasil))
}

enum class ScreenState {
    LOADING, EMPTY, CONTENT, BLANK
}
