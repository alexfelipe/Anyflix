package br.com.alura.anyflix.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.anyflix.dao.MovieDao
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.ui.screens.HomeScreen

internal const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToMovieDetails: (Movie) -> Unit,
) {
    composable(homeRoute) {
        HomeScreen(
            onMovieClick = onNavigateToMovieDetails,
        )
    }
}

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) {
    navigate(homeRoute, navOptions)
}