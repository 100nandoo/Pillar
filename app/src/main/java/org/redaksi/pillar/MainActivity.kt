package org.redaksi.pillar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import org.redaksi.ui.MainScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(items = listOf(stringResource(R.string.edisi), stringResource(R.string.artikel), stringResource(R.string.cari)))
        }
    }
}
