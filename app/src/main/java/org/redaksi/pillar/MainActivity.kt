package org.redaksi.pillar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import org.redaksi.ui.mainscreen.MainScreen
import org.redaksi.ui.PIllarTheme
import org.redaksi.ui.mainscreen.navBarItemList

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PIllarTheme(false) {
                MainScreen(
                    items = navBarItemList                )
            }
        }
    }
}
