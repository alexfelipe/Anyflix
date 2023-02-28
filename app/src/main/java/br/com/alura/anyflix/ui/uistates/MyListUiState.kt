package br.com.alura.anyflix.ui.uistates

import br.com.alura.anyflix.model.Movie

data class MyListUiState(
    val movies: List<Movie> = emptyList()
)