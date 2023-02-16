package br.com.alura.anyflix.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.ui.screens.HomeScreen

internal const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToMovieDetails: (Movie) -> Unit
) {
    composable(homeRoute) {
        HomeScreen(
            onMovieClick = onNavigateToMovieDetails
        )
    }
}

fun NavController.navigateToHome() {
    navigate(homeRoute)
}