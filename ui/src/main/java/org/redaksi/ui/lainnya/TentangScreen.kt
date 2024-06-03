package org.redaksi.ui.lainnya

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import org.redaksi.ui.R

@Composable
fun TentangScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        val state = rememberWebViewStateWithHTMLData(stringResource(id = R.string.tentang_html))
        WebView(
            modifier = Modifier
                .padding(it),
            state = state
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TentangScreenPreview() {
    TentangScreen()
}
