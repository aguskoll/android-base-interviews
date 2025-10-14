package com.aguskoll.androidcomposebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aguskoll.androidcomposebase.ui.pages.error.ErrorPage
import com.aguskoll.androidcomposebase.ui.pages.main.MainPage
import com.aguskoll.androidcomposebase.ui.theme.AppData

@Composable
fun MainNavHost() {
    NavHost(
        navController = AppData.mainNavController,
        startDestination = MainRoutes.MainPage.route
    ) {
        composable(route = MainRoutes.MainPage.route) { MainPage() }
        composable(
            route = MainRoutes.ErrorPage.route,
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            ErrorPage(message = backStackEntry.arguments?.getString("message") ?: "404")
        }
    }
}
