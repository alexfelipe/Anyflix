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
import br.com.alura.anyflix.ui.screens.MyListScreen

internal const val myListRoute = "myList"

fun NavGraphBuilder.myListScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToMovieDetails: (Movie) -> Unit
) {
    composable(myListRoute) {
        val dao = remember {
            MovieDao()
        }
        val myList by dao.myList.collectAsState(emptyList())
        MyListScreen(
            movies = myList,
            onSeeOtherMovies = onNavigateToHome,
            onRemoveMovieFromMyList = {
                dao.removeFromMyList(it)
            } ,
            onMovieClick = onNavigateToMovieDetails
        )
    }
}

fun NavController.navigateToMyList(navOptions: NavOptions? = null) {
    navigate(myListRoute, navOptions)
}