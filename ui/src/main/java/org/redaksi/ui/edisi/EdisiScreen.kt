package org.redaksi.ui.edisi

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EdisiScreen() {
    Scaffold {
        Text("Edisi")
    }
}

@Preview
@Composable
private fun EdisiScreenPreview(){
    EdisiScreen()
}
