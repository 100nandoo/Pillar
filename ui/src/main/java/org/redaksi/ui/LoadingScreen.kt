package org.redaksi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(isLoading: Boolean = true) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.background(PillarColor.background).fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
