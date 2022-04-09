package org.redaksi.pillar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.compose.NavHost
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.redaksi.pillar.BottomNavRoute.artikelRoute
import org.redaksi.pillar.BottomNavRoute.cariRoute
import org.redaksi.pillar.BottomNavRoute.edisiRoute
import org.redaksi.ui.R
import org.redaksi.ui.artikel.ArtikelScreen
import org.redaksi.ui.cari.CariScreen
import org.redaksi.ui.edisi.EdisiScreen

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navBarItemList, rememberNavController())
}

@Composable
fun MainScreen(items: List<NavBar>, navController: NavHostController) {
    Scaffold(
        bottomBar = { NavBar(topRoundedCornerModifier, items, navController) }
    ) {
        NavHost(navController, startDestination =artikelRoute ) {
            composable(artikelRoute) { ArtikelScreen() }
            composable(edisiRoute) { EdisiScreen() }
            composable(cariRoute) { CariScreen() }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    NavBar(topRoundedCornerModifier, navBarItemList, rememberNavController())
}

val navBarItemList = listOf(
    NavBar(R.string.artikel, R.drawable.ic_artikel, artikelRoute),
    NavBar(R.string.edisi, R.drawable.ic_edisi, edisiRoute),
    NavBar(R.string.cari, R.drawable.ic_cari, cariRoute)
)

object BottomNavRoute {
    const val artikelRoute = "artikel"
    const val edisiRoute = "edisi"
    const val cariRoute = "cari"
}

val topRoundedCornerModifier = Modifier.clip(RoundedCornerShape(16, 16, 0, 0))

@Composable
fun NavBar(modifier: Modifier, items: List<NavBar>, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(modifier = modifier) {
        items.forEachIndexed { _, item ->

            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { onClickNavBarItem(navController, item) }
            )
        }
    }
}

private fun onClickNavBarItem(navController: NavController, navbar: NavBar) {
    navController.navigate(navbar.route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
