package br.com.alura.anyflix.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.anyflix.dao.MovieDao
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.screens.MovieDetailsScreen
import br.com.alura.anyflix.ui.uistates.MovieDetailsUiState

internal const val movieDetailsRoute = "movieDetails"
private const val movieIdArgument = "movieId"
internal const val movieDetailsRouteFullpath = "$movieDetailsRoute/{$movieIdArgument}"

fun NavGraphBuilder.movieDetailsScreen(
    onNavigateToMovieDetails: (Movie) -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(movieDetailsRouteFullpath) { backStackEntry ->
        val movieId = backStackEntry.arguments?.getString(movieIdArgument)
        sampleMovies.find { movie ->
            movie.id == movieId
        }?.let { movie ->
            val dao = remember {
                MovieDao()
            }
            val myList by dao.myList.collectAsState(emptyList())
            val movies by dao.movies.collectAsState()
            val suggestedMovies = remember(movie) {
                movies.shuffled().take(10)
            }
            val isMovieAddedToMyList = remember(myList) {
                myList.contains(movie)
            }
            val uiState = remember(
                suggestedMovies,
                isMovieAddedToMyList
            ) {
                MovieDetailsUiState(
                    movie = movie,
                    isMovieAddedToMyList = isMovieAddedToMyList,
                    suggestedMovies = suggestedMovies
                )
            }
            MovieDetailsScreen(
                uiState = uiState,
                onMovieClick = onNavigateToMovieDetails,
                onAddToMyListClick = {
                    dao.addToMyList(it)
                },
                onRemoveFromMyList = {
                    dao.removeFromMyList(it)
                }
            )
        } ?: LaunchedEffect(null) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToMovieDetails(
    id: String,
    navOptions: NavOptions? = null
) {
    navigate("$movieDetailsRoute/$id", navOptions)
}