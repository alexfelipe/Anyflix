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
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var currentUiStateJob: Job? = null
    private val _uiState = MutableStateFlow(
        HomeUiState()
    )
    val uiState = _uiState.asStateFlow()

    init {
        loadUiState()
    }

    private fun loadUiState() {
        currentUiStateJob?.cancel()
        currentUiStateJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(Random.nextLong(250, 1000))
            repository.findSections()
                .catch {
                    _uiState.value = _uiState.value.copy(
                        error = "falha ao carregar os filmes"
                    )
                }
                .collect { result ->
                    result.data?.let { sections ->
                        if (sections.isEmpty()) {
                            delay(2000)
                            _uiState.value = _uiState.value.copy(
                                isLoading = false
                            )
                        } else {
                            val movie = sections
                                .entries.random()
                                .value.random()
                            _uiState.value = _uiState.value.copy(
                                sections = sections,
                                mainBannerMovie = movie,
                                isLoading = false
                            )
                        }
                    }
                    result.exception?.let { exception ->
                        when (exception) {
                            is ConnectException -> {
                                _uiState.value = _uiState.value.copy(
                                    error = "falha ao conectar na API"
                                )
                            }
                            is SocketTimeoutException -> {
                                _uiState.value = _uiState.value.copy(
                                    error = "tempo excedido com a API"
                                )
                            }
                        }
                        launch {
                            delay(5000)
                            _uiState.value = _uiState.value.copy(
                                error = null
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