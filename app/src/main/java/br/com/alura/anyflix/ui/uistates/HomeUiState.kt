package br.com.alura.anyflix.ui.uistates

import br.com.alura.anyflix.model.Movie

data class HomeUiState(
    val mainBannerMovie: Movie? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val sections: Map<String, List<Movie>>? = null
)