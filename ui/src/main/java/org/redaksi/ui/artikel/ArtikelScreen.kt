package org.redaksi.ui.artikel

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ArtikelScreen() {
    Scaffold {
        Text("Artikel")
    }
}

@Preview
@Composable
private fun ArtikelScreenPreview(){
    ArtikelScreen()
}
