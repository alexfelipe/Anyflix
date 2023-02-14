package br.com.alura.anyflix.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.ui.uistates.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: MovieDao
) : ViewModel() {

    private var currentUiStateJob: Job? = null
    private val _uiState = MutableStateFlow<HomeUiState>(
        HomeUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadUiState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadUiState() {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            delay(Random.nextLong(250, 1000))
            dao.findAll()
                .map {
                    it.map { entity ->
                        entity.toMovie()
                    }
                }.flatMapLatest { movies ->
                    flow {
                        if (movies.isEmpty()) {
                            emit(emptyMap())
                        } else {
                            emit(createSections(movies))
                        }
                    }
                }
                .catch {
                    _uiState.update {
                        HomeUiState.Failure
                    }
                }
                .collect { sections ->
                    if (sections.isEmpty()) {
                        delay(2000)
                        _uiState.update {
                            HomeUiState.Empty
                        }
                    } else {
                        val movie = sections
                            .entries.random()
                            .value.random()
                        _uiState.update {
                            HomeUiState.Success(
                                sections = sections,
                                mainBannerMovie = movie
                            )
                        }
                    }
                }
        }
    }

    fun loadSections() {
        loadUiState()
    }

    private fun createSections(movies: List<Movie>) = mapOf(
        "Em alta" to movies.shuffled().take(7),
        "Novidades" to movies.shuffled().take(7),
        "Continue assistindo" to movies.shuffled().take(7)
    )

}