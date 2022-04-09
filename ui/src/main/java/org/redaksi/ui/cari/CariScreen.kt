package org.redaksi.ui.cari

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CariScreen() {
    Scaffold {
        Text("Cari")
    }
}

@Preview
@Composable
private fun CariScreenPreview(){
    CariScreen()
}
