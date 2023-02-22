package br.com.alura.anyflix.dao

import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MovieDao {

    val myList = _myList
        .map { it.toList() }
    val movies =
        _movies.asStateFlow()

    fun addToMyList(movie: Movie) {
        _myList.update {
            it + movie
        }
    }

    fun removeFromMyList(movie: Movie) {
        _myList.update {
            it - movie
        }
    }

    companion object {
        private val _myList = MutableStateFlow<Set<Movie>>(emptySet())
        private val _movies = MutableStateFlow(sampleMovies)
    }

}