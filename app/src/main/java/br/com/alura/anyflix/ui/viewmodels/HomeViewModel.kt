package br.com.alura.anyflix.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.anyflix.dao.LocalMovieDao
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.sampleData.sampleMovies
import br.com.alura.anyflix.ui.uistates.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dao: MovieDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dao.findAll().map {
                it.map { entity ->
                    entity.toMovie()
                }
            }.collect { movies ->
                val sections = mapOf(
                    "Em alta" to movies.shuffled().take(7),
                    "Novidades" to movies.shuffled().take(7),
                    "Continue assistindo" to movies.shuffled().take(7)
                )
                _uiState.update {
                    it.copy(sections = sections)
                }
            }
        }
    }

}