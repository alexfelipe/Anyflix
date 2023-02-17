package br.com.alura.anyflix.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.alura.anyflix.dao.MovieDao
import br.com.alura.anyflix.ui.screens.UserMoviesScreen

internal const val userMoviesRoute = "userMovies"

fun NavGraphBuilder.userMoviesScreen(
    onNavigateToUserMovies: () -> Unit,
) {
    composable(userMoviesRoute) {
        val dao = remember {
            MovieDao()
        }
        val movies by dao.movies.collectAsState(emptyList())
        UserMoviesScreen(
            movies = movies,
            onSeeOtherMovies = onNavigateToUserMovies
        )
    }
}

fun NavController.navigateToUserMovies() {
    navigate(userMoviesRoute)
}