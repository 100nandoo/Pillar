package org.redaksi.pillar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.redaksi.pillar.BottomNavRoute.ARTIKEL_DETAIL_ROUTE
import org.redaksi.pillar.BottomNavRoute.ARTIKEL_ID_ARG
import org.redaksi.pillar.BottomNavRoute.ARTIKEL_ROUTE
import org.redaksi.pillar.BottomNavRoute.CARI_ROUTE
import org.redaksi.pillar.BottomNavRoute.LAINNYA_ROUTE
import org.redaksi.pillar.BottomNavRoute.TENTANG_ROUTE
import org.redaksi.pillar.BottomNavRoute.UTAMA_ROUTE
import org.redaksi.ui.R
import org.redaksi.ui.artikel.ArtikelScreen
import org.redaksi.ui.artikel.detail.ArtikelDetailScreen
import org.redaksi.ui.cari.CariScreen
import org.redaksi.ui.compose.PillarColor.bottomBarSelected
import org.redaksi.ui.compose.PillarColor.primary
import org.redaksi.ui.compose.PillarColor.secondary
import org.redaksi.ui.lainnya.LainnyaScreen
import org.redaksi.ui.lainnya.TentangScreen
import org.redaksi.ui.utama.UtamaScreen

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navBarItemList, rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(items: List<NavBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == UTAMA_ROUTE || currentRoute == ARTIKEL_ROUTE || currentRoute == CARI_ROUTE || currentRoute == LAINNYA_ROUTE) {
                NavBar(topRoundedCornerModifier, items, navController)
            }
        }
    ) { paddingValues ->
        NavHost(navController, startDestination = UTAMA_ROUTE) {
            composable(UTAMA_ROUTE) { UtamaScreen(paddingValues, onClick = { navController.navigate("$ARTIKEL_DETAIL_ROUTE/$it") }) }
            composable(ARTIKEL_ROUTE) { ArtikelScreen(paddingValues, onClickArtikel = { navController.navigate("$ARTIKEL_DETAIL_ROUTE/$it") }) }
            composable(CARI_ROUTE) { CariScreen(paddingValues, onClick = { navController.navigate("$ARTIKEL_DETAIL_ROUTE/$it") }) }
            composable(LAINNYA_ROUTE) { LainnyaScreen(paddingValues, onClickTentang = { navController.navigate(TENTANG_ROUTE) }) }
            composable(TENTANG_ROUTE) { TentangScreen() }
            composable(
                route = "$ARTIKEL_DETAIL_ROUTE/{$ARTIKEL_ID_ARG}",
                arguments = listOf(navArgument(ARTIKEL_ID_ARG) { type = NavType.IntType })
            ) { ArtikelDetailScreen() }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    NavBar(topRoundedCornerModifier, navBarItemList, rememberNavController())
}

val navBarItemList = listOf(
    NavBarItem(R.string.utama, R.drawable.ic_utama, UTAMA_ROUTE),
    NavBarItem(R.string.artikel, R.drawable.ic_artikel, ARTIKEL_ROUTE),
    NavBarItem(R.string.cari, R.drawable.ic_cari, CARI_ROUTE),
    NavBarItem(R.string.lainnya, R.drawable.ic_lainnya, LAINNYA_ROUTE)
)

object BottomNavRoute {
    const val UTAMA_ROUTE = "utama"
    const val ARTIKEL_ROUTE = "artikel"
    const val ARTIKEL_DETAIL_ROUTE = "artikelDetail"
    const val CARI_ROUTE = "cari"
    const val TENTANG_ROUTE = "tentang"
    const val LAINNYA_ROUTE = "lainnya"

    const val ARTIKEL_ID_ARG = "artikelId"
}

const val BOTTOM_NAV_CORNER = 16
val topRoundedCornerModifier = Modifier.clip(RoundedCornerShape(BOTTOM_NAV_CORNER, BOTTOM_NAV_CORNER, 0, 0))

@Composable
fun NavBar(modifier: Modifier, items: List<NavBarItem>, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(modifier = modifier, containerColor = primary) {
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = stringResource(item.label))
                },
                label = { Text(stringResource(item.label)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { onClickNavBarItem(navController, item) },
                colors = NavigationBarItemDefaults.colors(
                    primary,
                    secondary,
                    unselectedTextColor = secondary,
                    unselectedIconColor = secondary,
                    indicatorColor = bottomBarSelected
                )
            )
        }
    }
}

private fun onClickNavBarItem(navController: NavController, navbar: NavBarItem) {
    navController.navigate(navbar.route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
