package br.com.alura.anyflix.repositories

import android.util.Log
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.di.modules.ApplicationScope
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.network.MoviesRestApi
import br.com.alura.anyflix.network.toMovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MoviesRepository"

class MoviesRepository @Inject constructor(
    private val dao: MovieDao,
    private val restApi: MoviesRestApi,
    @ApplicationScope private val scope: CoroutineScope
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun findSections(): Flow<Map<String, List<Movie>>> =
        findAll()
            .flatMapLatest { movies ->
                flow {
                    if (movies.isEmpty()) {
                        emit(emptyMap())
                    } else {
                        emit(createSections(movies))
                    }
                }
            }

    fun myList(): Flow<List<Movie>> {
        scope.launch {
            try {
                fetchMyListFromRestAPI()
            } catch (e: Exception) {
                Log.e(TAG, "myList: ", e)
            }
        }
        return dao.myList()
            .map { entities ->
                entities.map {
                    it.toMovie()
                }
            }
    }

    suspend fun removeFromMyList(id: String) {
        restApi.removeFromMyList(id)
        dao.removeFromMyList(id)
    }

    suspend fun addToMyList(id: String) {
        restApi.addToMyList(id)
        dao.addToMyList(id)
    }

    fun findMovieById(id: String): Flow<Movie> {
        scope.launch {
            try {
                restApi.findMovieById(id)
            } catch (e: Exception) {
                Log.e(TAG, "findMovieById: ", e)
            }
        }
        return dao.findMovieById(id)
            .map { entity -> entity.toMovie() }
    }

    private fun createSections(movies: List<Movie>) = mapOf(
        "Em alta" to movies.shuffled().take(7),
        "Novidades" to movies.shuffled().take(7),
        "Continue assistindo" to movies.shuffled().take(7)
    )

    fun suggestedMovies(id: String) =
        dao.suggestedMovies(id)

    private fun findAll(): Flow<List<Movie>> {
        scope.launch {
            try {
                fetchMoviesFromRestAPI()
            } catch (e: Exception) {
                Log.e(TAG, "findAll: ", e)
            }
        }
        return dao.findAll()
            .map { entities ->
                entities.map {
                    it.toMovie()
                }
            }
    }

    private suspend fun fetchMoviesFromRestAPI() {
        restApi.findAll().map {
            it.toMovieEntity()
        }.let { entities ->
            dao.saveAll(*entities.toTypedArray())
        }
    }

    private suspend fun fetchMyListFromRestAPI() {
        restApi.myList().map {
            it.toMovieEntity()
        }.let { entities ->
            dao.saveAll(*entities.toTypedArray())
        }
    }

}