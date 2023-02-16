package br.com.alura.anyflix.dao

import br.com.alura.anyflix.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDao {

    val movies = Companion._movies.asStateFlow()

    fun add(movie: Movie) {
        _movies.update {
            it + movie
        }
    }

    companion object {
        private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    }

}