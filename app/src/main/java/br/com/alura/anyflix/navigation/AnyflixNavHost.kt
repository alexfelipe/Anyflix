package br.com.alura.anyflix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

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
            },
        )
        movieDetailsScreen(
            onNavigateToMovieDetails = { movie ->
                navController.navigateToMovieDetails(movie.id,
                    navOptions {
                        popUpTo(movieDetailsRouteFullpath) {
                            inclusive = true
                        }
                    }
                )
            },
            onPopBackStack = {
                navController.popBackStack()
            }
        )
        myListScreen(
            onNavigateToHome = {
                navController.navigateToHome(navOptions {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                    }
                })
            },
            onNavigateToMovieDetails = {
                navController.navigateToMovieDetails(
                    it.id,
                    navOptions {
                        popUpTo(movieDetailsRouteFullpath) {
                            inclusive = true
                        }
                    }
                )
            }
        )
    }
}