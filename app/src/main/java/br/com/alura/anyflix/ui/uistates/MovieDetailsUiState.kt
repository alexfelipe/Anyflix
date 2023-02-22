package br.com.alura.anyflix.ui.uistates

import br.com.alura.anyflix.model.Movie

data class MovieDetailsUiState(
    val movie: Movie? = null,
    val isMovieAddedToFavoriteList: Boolean = false,
    val suggestedMovies: List<Movie> = emptyList()
)
