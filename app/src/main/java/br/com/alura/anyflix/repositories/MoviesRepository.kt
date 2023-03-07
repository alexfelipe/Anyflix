package br.com.alura.anyflix.repositories

import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.di.modules.ApplicationScope
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.network.MoviesRestApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val dao: MovieDao,
    private val restApi: MoviesRestApi,
    @ApplicationScope private val scope: CoroutineScope
) {

    fun findAll(): Flow<List<Movie>> {
        return dao.findAll()
            .map { entities ->
                entities.map {
                    it.toMovie()
                }
            }
    }

    fun myList() = dao.myList()

    suspend fun removeFromMyList(id: String) {
        restApi.removeFromMyList(id)
        dao.removeFromMyList(id)
    }

    suspend fun addToMyList(id: String) {
        restApi.addToMyList(id)
        dao.removeFromMyList(id)
    }

    fun findMovieById(id: String): Flow<Movie> {
        scope.launch {
            restApi.findMovieById(id)
        }
        return dao.findMovieById(id)
            .map { entity -> entity.toMovie() }
    }

}