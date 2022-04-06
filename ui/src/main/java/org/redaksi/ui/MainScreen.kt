package org.redaksi.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(listOf("Artikel", "Edisi", "Cari"))
}

@Composable
fun MainScreen(items: List<String>) {
    Scaffold(
        bottomBar = { NavBar(items = items) }
    ) {
        Text("Compose Text")
    }
}

@Preview
@Composable
fun NavBarPreview() {
    NavBar(listOf("Artikel", "Edisi", "Cari"))
}

@Composable
fun NavBar(items: List<String>) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}
