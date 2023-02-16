package br.com.alura.anyflix.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.screens.MovieDetailsScreen

internal const val movieDetailsRoute = "movieDetails"
private const val movieIdArgument = "movieId"

fun NavGraphBuilder.movieDetailsScreen(
    onNavigateToMovieDetails: (Movie) -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable("$movieDetailsRoute/{$movieIdArgument}") { backStackEntry ->
        val movieId = backStackEntry.arguments?.getString(movieIdArgument)
        sampleMovies.find { movie ->
            movie.id == movieId
        }?.let { movie ->
            MovieDetailsScreen(
                movie = movie,
                onMovieClick = onNavigateToMovieDetails
            )
        } ?: LaunchedEffect(null) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToMovieDetails(id: String) {
    navigate("$movieDetailsRoute/$id")
}