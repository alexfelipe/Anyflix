package br.com.alura.anyflix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AnyflixNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = homeRoute
    ) {
        homeScreen(
            onNavigateToMovieDetails = { movie ->
                navController.navigateToMovieDetails(movie.id)
            }
        )
        movieDetailsScreen(
            onNavigateToMovieDetails = { movie ->
                navController.navigateToMovieDetails(movie.id)
            },
            onPopBackStack = {
                navController.popBackStack()
            }
        )
    }
}