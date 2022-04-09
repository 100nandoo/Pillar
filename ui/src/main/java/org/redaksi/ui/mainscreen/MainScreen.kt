package org.redaksi.ui.mainscreen

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.redaksi.ui.R

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navBarItemList)
}

@Composable
fun MainScreen(items: List<NavBar>) {
    Scaffold(
        bottomBar = { NavBar(topRoundedCornerModifier, items = items) }
    ) {
        Text("Compose Text")
    }
}

@Preview
@Composable
fun NavBarPreview() {
    NavBar(topRoundedCornerModifier, navBarItemList)
}

val navBarItemList = listOf(
    NavBar(R.string.artikel, R.drawable.ic_artikel),
    NavBar(R.string.edisi, R.drawable.ic_edisi),
    NavBar(R.string.cari, R.drawable.ic_cari)
)

val topRoundedCornerModifier = Modifier.clip(RoundedCornerShape(16, 16, 0,0))

@Composable
fun NavBar(modifier: Modifier, items: List<NavBar>) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(modifier = modifier) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}
