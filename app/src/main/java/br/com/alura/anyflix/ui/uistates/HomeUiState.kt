package br.com.alura.anyflix.ui.uistates

import br.com.alura.anyflix.model.Movie

data class HomeUiState(
    val sections: Map<String, List<Movie>> = emptyMap(),
)