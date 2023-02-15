package br.com.alura.anyflix.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.anyflix.ui.screens.HomeScreen

internal const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(navController: NavHostController) {
    composable(homeRoute) {
        HomeScreen(
            onMovieClick = { movie ->
                navController.navigateToMovieDetails(movie.id)
            }
        )
    }
}

fun NavController.navigateToHome() {
    navigate(homeRoute)
}