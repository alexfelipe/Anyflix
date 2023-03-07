package br.com.alura.anyflix.network

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRestApi @Inject constructor(
    private val service: MoviesService
) {

    fun findAll() = flow {
        emit(service.findAll())
    }

    suspend fun addToMyList(id: String){
        service.addToMyList(id)
    }

    suspend fun removeFromMyList(id: String) {
        service.removeFromMyList(id)
    }

    suspend fun findMovieById(id: String) {
        service.findMovieById(id)
    }

}