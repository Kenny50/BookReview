package com.kenny.bookreview.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kenny.bookreview.ui.screen.SearchScreen
import com.kenny.bookreview.ui.screen.home.HomeScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val bottomNavList = listOf(BottomNavScreen.Home, BottomNavScreen.Search)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarVisibility = rememberSaveable { (mutableStateOf(true)) }

    bottomBarVisibility.value =
        bottomNavList.map { it.route }.contains(navBackStackEntry?.destination?.route)

    BottomNavigationImpl(
        navController,
        bottomNavList,
        navBackStackEntry,
        bottomBarVisibility.value
    ) {
        NavHost(navController = navController, startDestination = BottomNavScreen.Home.route) {
            buildBottomScreen(navController)

        }
    }
}

private fun NavGraphBuilder.buildBottomScreen(navController: NavController) {
    composable(route = BottomNavScreen.Home.route) {
        HomeScreen { bookId ->
            navController.navigate(Screen.BookDetailScreen.withArgs(bookId.toString()))
        }
    }

    composable(route = BottomNavScreen.Search.route) {
        SearchScreen()
    }
}

@Composable
private fun BottomNavigationImpl(
    navController: NavController,
    items: List<BottomNavScreen>,
    navBackStackEntry: NavBackStackEntry?,
    shouldShowBottomNavigation: Boolean,
    navHost: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            AnimateBottomNavBar(
                shouldShowBottomNavigation,
                navBackStackEntry,
                navController,
                items
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            navHost()
        }
    }
}

@Composable
private fun AnimateBottomNavBar(
    shouldShowBottomNavigation: Boolean,
    navBackStackEntry: NavBackStackEntry?,
    navController: NavController,
    items: List<BottomNavScreen>
) {
    AnimatedVisibility(
        visible = shouldShowBottomNavigation,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, contentDescription = "") },
                        label = { Text(text = stringResource(id = screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

            }
        }
    )
}

