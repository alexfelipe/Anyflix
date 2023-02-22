package br.com.alura.anyflix.dao

import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MovieDao {

    val favoriteMovies = _favoriteMovies
        .map { it.toList() }
    val movies =
        _movies.asStateFlow()

    fun addToFavoriteMovies(movie: Movie) {
        _favoriteMovies.update {
            it + movie
        }
    }

    fun removeFromFavoriteMovies(movie: Movie) {
        _favoriteMovies.update {
            it - movie
        }
    }

    companion object {
        private val _favoriteMovies = MutableStateFlow<Set<Movie>>(emptySet())
        private val _movies = MutableStateFlow(sampleMovies)
    }

}