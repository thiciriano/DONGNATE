package com.dongnate.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dongnate.ui.screens.HomeScreen
import com.dongnate.ui.screens.RequestDetailScreen
import com.dongnate.ui.screens.auth.LoginScreen
import com.dongnate.ui.screens.auth.RegisterOngScreen
import com.dongnate.ui.screens.auth.RegisterScreen
import com.dongnate.ui.screens.donor.MyInterestsScreen
import com.dongnate.ui.screens.ong.CreateRequestScreen
import com.dongnate.ui.screens.ong.MyRequestsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }
        composable(
            route = Routes.REGISTER_ONG + "/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) {
            RegisterOngScreen(navController, it.arguments?.getInt("userId") ?: 0)
        }
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.MY_REQUESTS) { MyRequestsScreen(navController) }
        composable(Routes.CREATE_REQUEST) { CreateRequestScreen(navController) }
        composable(
            route = Routes.REQUEST_DETAIL,
            arguments = listOf(navArgument("requestId") { type = NavType.IntType })
        ) {
            RequestDetailScreen(navController, it.arguments?.getInt("requestId") ?: 0)
        }
        composable(Routes.MY_INTERESTS) { MyInterestsScreen(navController) }
    }
}
