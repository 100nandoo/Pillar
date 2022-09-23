package org.redaksi.pillar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import org.redaksi.pillar.BottomNavRoute.artikelDetailRoute
import org.redaksi.pillar.BottomNavRoute.artikelIdArg
import org.redaksi.pillar.BottomNavRoute.artikelRoute
import org.redaksi.pillar.BottomNavRoute.buatKomentarRoute
import org.redaksi.pillar.BottomNavRoute.cariRoute
import org.redaksi.pillar.BottomNavRoute.edisiDetailRoute
import org.redaksi.pillar.BottomNavRoute.edisiRoute
import org.redaksi.pillar.BottomNavRoute.issueNumberArg
import org.redaksi.pillar.BottomNavRoute.komentarRoute
import org.redaksi.pillar.BottomNavRoute.lainnyaRoute
import org.redaksi.pillar.BottomNavRoute.tanggapanRoute
import org.redaksi.pillar.BottomNavRoute.tentangRoute
import org.redaksi.ui.PillarColor.bottomBarSelected
import org.redaksi.ui.PillarColor.primary
import org.redaksi.ui.PillarColor.secondaryVar
import org.redaksi.ui.PillarColor.surface
import org.redaksi.ui.R
import org.redaksi.ui.TentangScreen
import org.redaksi.ui.artikel.ArtikelScreen
import org.redaksi.ui.artikel.detail.ArtikelDetailScreen
import org.redaksi.ui.cari.CariScreen
import org.redaksi.ui.edisi.EdisiScreen
import org.redaksi.ui.edisi.detail.EdisiDetailScreen
import org.redaksi.ui.komentar.KomentarScreen
import org.redaksi.ui.komentar.buat.BuatKomentarScreen
import org.redaksi.ui.lainnya.LainnyaScreen
import org.redaksi.ui.tanggapan.TanggapanScreen

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navBarItemList, rememberNavController())
}

@Composable
fun MainScreen(items: List<NavBarItem>, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute == edisiRoute || currentRoute == artikelRoute || currentRoute == cariRoute || currentRoute == lainnyaRoute) {
                NavBar(topRoundedCornerModifier, items, navController)
            }
        }
    ) { paddingValues ->
        NavHost(navController, startDestination = edisiRoute) {
            composable(edisiRoute) { EdisiScreen(onClick = { navController.navigate("$edisiDetailRoute/$it") }) }
            composable(artikelRoute) { ArtikelScreen(paddingValues, onClickArtikel = { navController.navigate("$artikelDetailRoute/$it") }) }
            composable(cariRoute) { CariScreen(paddingValues, onClick = { navController.navigate("$artikelDetailRoute/$it") }) }
            composable(tanggapanRoute) { TanggapanScreen(paddingValues, onClick = { navController.navigate("$artikelDetailRoute/$it") }) }
            composable(lainnyaRoute) {
                LainnyaScreen(
                    paddingValues,
                    { navController.navigate(tanggapanRoute) },
                    { navController.navigate(tentangRoute) }
                )
            }
            composable(tentangRoute) { TentangScreen() }
            composable("$edisiDetailRoute/{$issueNumberArg}") {
                EdisiDetailScreen(paddingValues, onClick = { navController.navigate("$artikelDetailRoute/$it") })
            }
            composable(
                route = "$artikelDetailRoute/{$artikelIdArg}",
                arguments = listOf(navArgument(artikelIdArg) { type = NavType.IntType })
            ) { ArtikelDetailScreen(onClickKomentar = { navController.navigate("$komentarRoute/$it") }) }

            composable(
                route = "$komentarRoute/{$artikelIdArg}",
                arguments = listOf(navArgument(artikelIdArg) { type = NavType.IntType })
            ) { KomentarScreen(onClickBuatKomentar = { navController.navigate("$buatKomentarRoute/$it") }) }

            composable(
                route = "$buatKomentarRoute/{$artikelIdArg}",
                arguments = listOf(navArgument(artikelIdArg) { type = NavType.IntType })
            ) {
                BuatKomentarScreen(onKomentarInserted = {
                    navController.popBackStack("$komentarRoute/{$artikelIdArg}", false)
                })
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview() {
    NavBar(topRoundedCornerModifier, navBarItemList, rememberNavController())
}

val navBarItemList = listOf(
    NavBarItem(R.string.edisi, R.drawable.ic_edisi, edisiRoute),
    NavBarItem(R.string.artikel, R.drawable.ic_artikel, artikelRoute),
    NavBarItem(R.string.cari, R.drawable.ic_cari, cariRoute),
    NavBarItem(R.string.lainnya, R.drawable.ic_lainnya, lainnyaRoute)
)

object BottomNavRoute {
    const val edisiRoute = "edisi"
    const val edisiDetailRoute = "edisiDetail"
    const val artikelRoute = "artikel"
    const val artikelDetailRoute = "artikelDetail"
    const val cariRoute = "cari"
    const val tentangRoute = "tentang"
    const val lainnyaRoute = "lainnya"
    const val tanggapanRoute = "tanggapan"
    const val komentarRoute = "komentar"
    const val buatKomentarRoute = "buatKomentar"

    const val issueNumberArg = "issueNumber"
    const val artikelIdArg = "artikelId"
}

const val BOTTOM_NAV_CORNER = 16
val topRoundedCornerModifier = Modifier.clip(RoundedCornerShape(BOTTOM_NAV_CORNER, BOTTOM_NAV_CORNER, 0, 0))

@Composable
fun NavBar(modifier: Modifier, items: List<NavBarItem>, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(modifier = modifier, containerColor = primary) {
        items.forEachIndexed { _, item ->
            val color = if (currentDestination?.hierarchy?.any { it.route == item.route } == true) surface else secondaryVar

            NavigationBarItem(
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = stringResource(item.label))
                },
                label = { Text(stringResource(item.label), color = color) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = { onClickNavBarItem(navController, item) },
                colors = NavigationBarItemDefaults.colors(surface, secondaryVar, indicatorColor = bottomBarSelected)
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
