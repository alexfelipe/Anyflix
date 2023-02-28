package br.com.alura.anyflix.dao

import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.sampleData.sampleMovieSections
import br.com.alura.anyflix.sampleData.sampleMovies
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LocalMovieDao @Inject constructor() {

    val myList = _myList
        .map { it.toList() }
        .stateIn(
            MainScope(),
            SharingStarted.Eagerly,
            emptyList()
        )

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

    fun suggestedMovies() = flow {
        emit(_movies.value - _myList.value)
    }

    fun findMovieById(id: String): Flow<Movie> =
        _movies.map { movies -> movies.first { it.id == id } }

    fun isMovieAddedToMyList(movie: Movie) = _myList.map { movies ->
        movie in movies
    }

    companion object {
        private val _myList = MutableStateFlow<Set<Movie>>(emptySet())
        private val _movies = MutableStateFlow(sampleMovies)
        private val _sections = MutableStateFlow(sampleMovieSections)
    }

}