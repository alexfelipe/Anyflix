package br.com.alura.anyflix.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.anyflix.dao.LocalMovieDao
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.ui.uistates.MyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val dao: MovieDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyListUiState())

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.myList()
                .map {
                    it.map { entities ->
                        entities.toMovie()
                    }
                }
                .collect { movies ->
                    _uiState.update {
                        it.copy(movies = movies)
                    }
                }
        }
    }

    suspend fun removeFromMyList(movie: Movie) {
        dao.removeFromMyList(movie.id)
    }

}