package br.com.alura.anyflix.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.anyflix.ui.screens.MovieDetailsScreen

internal const val movieDetailsRoute = "movieDetails"

fun NavGraphBuilder.movieDetailsScreen() {
    composable(movieDetailsRoute) {
        MovieDetailsScreen()
    }
}

fun NavController.navigateToMovieDetails(id: String){
    navigate(movieDetailsRoute)
}