package br.com.alura.anyflix.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.anyflix.repositories.MovieRepository
import br.com.alura.anyflix.ui.uistates.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var currentUiStateJob: Job? = null
    private val _uiState = MutableStateFlow<HomeUiState>(
        HomeUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadUiState()
    }

    private fun loadUiState() {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            delay(Random.nextLong(250, 1000))
            repository.findSections()
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


}