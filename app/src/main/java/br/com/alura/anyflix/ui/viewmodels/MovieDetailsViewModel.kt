package br.com.alura.anyflix.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.navigation.movieIdArgument
import br.com.alura.anyflix.repositories.MoviesRepository
import br.com.alura.anyflix.ui.uistates.MovieDetailsUiState
import br.com.alura.anyflix.ui.uistates.MovieDetailsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : ViewModel() {
    private var currentUiStateJob: Job? = null

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(
        MovieDetailsUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadUiState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadUiState() {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            _uiState.update { MovieDetailsUiState.Loading }
            delay(Random.nextLong(500, 1000))
            repository.findMovieById(
                requireNotNull(
                    savedStateHandle[movieIdArgument]
                )
            ).flatMapLatest<Movie, MovieDetailsUiState> { movie ->
                repository.suggestedMovies(movie.id)
                    .map { suggestedMovies ->
                        Success(
                            movie = movie,
                            suggestedMovies = suggestedMovies,
                        )
                    }
            }.catch {
                emit(MovieDetailsUiState.Failure)
            }.collectLatest { uiState ->
                _uiState.emit(uiState)
            }
        }
    }

    suspend fun addToMyList(movie: Movie) {
        repository.addToMyList(movie.id)
    }

    suspend fun removeFromMyList(movie: Movie) {
        repository.removeFromMyList(movie.id)
    }

    fun loadMovie() {
        loadUiState()
    }

}