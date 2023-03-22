package br.com.alura.anyflix.repositories

import android.util.Log
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.MovieEntity
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.database.entities.toMovieRequest
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.network.MovieService
import br.com.alura.anyflix.network.toMovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(
    private val dao: MovieDao,
    private val service: MovieService
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun findSections(): Flow<Map<String, List<Movie>>> {
        CoroutineScope(coroutineContext).launch {
            try {
                val moviesTobeSynchronized = dao.findAllMoviesNotSynchronized()
                Log.i(TAG, "findSections: moviesToBeSynchronized -> $moviesTobeSynchronized")
                val moviesToBeSave = mutableListOf<MovieEntity>()
                moviesTobeSynchronized.forEach { entity ->
                    val id = entity.id
                    val request = entity.toMovieRequest()
                    val updatedMovie = service
                        .update(id, request)
                        .toMovieEntity()
                    moviesToBeSave.add(updatedMovie)
                }
                Log.i(TAG, "findSections: moviesToBeSave -> $moviesToBeSave")
                val response = service.findAll()
                val movies = response.map {
                    it.toMovieEntity()
                }
                dao.saveAll(*movies.toTypedArray())
            } catch (e: ConnectException) {
                Log.e(TAG, "findSections: falha ao conectar na API", e)
            }
        }
        return dao.findAll()
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

    }

    suspend fun myList(): Flow<List<Movie>> {
        CoroutineScope(coroutineContext).launch {
            try {
                val myList = service.findMyList()
                    .map { it.toMovieEntity() }
                dao.saveAll(*myList.toTypedArray())
            } catch (e: ConnectException) {
                Log.e(TAG, "myList: falha ao conectar na API", e)
            } catch (e: HttpException) {
                Log.e(TAG, "myList: não encontrou filmes na minha lista", e)
            }
        }
        return dao.myList()
            .map { it.map { entity -> entity.toMovie() } }
    }

    suspend fun findMovieById(id: String): Flow<Movie> {
        CoroutineScope(coroutineContext).launch {
            try {
                val response = service.findMovieById(id)
                dao.save(response.toMovieEntity())
            } catch (e: ConnectException) {
                Log.e(TAG, "findMovieById: falha ao conectar na API", e)
            } catch (e: HttpException) {
                Log.e(TAG, "findMovieById: não encontrou o filme a partir do id: $id", e)
            }
        }
        return dao.findMovieById(id)
            .map { it.toMovie() }
    }

    private fun createSections(movies: List<Movie>) = mapOf(
        "Em alta" to movies.shuffled().take(7),
        "Novidades" to movies.shuffled().take(7),
        "Continue assistindo" to movies.shuffled().take(7)
    )

    fun suggestedMovies(id: String): Flow<List<Movie>> =
        dao.suggestedMovies(id)
            .map { it.map { entity -> entity.toMovie() } }

    suspend fun removeFromMyList(id: String) {
        CoroutineScope(coroutineContext).launch {
            try {
                service.removeFromMyList(id)
            } catch (e: ConnectException) {
                Log.e(TAG, "addToMyList: falha ao conectar na API", e)
            } catch (e: HttpException) {
                Log.e(TAG, "addToMyList: não encontrou o filme a partir do id: $id", e)
            }
            launch {
                dao.removeFromMyList(id)
            }
        }
    }

    suspend fun addToMyList(id: String) {
        CoroutineScope(coroutineContext).launch {
            try {
                service.addToMyList(id)
            } catch (e: ConnectException) {
                Log.e(TAG, "addToMyList: falha ao conectar na API", e)
            } catch (e: HttpException) {
                Log.e(TAG, "addToMyList: não encontrou o filme a partir do id: $id", e)
            }
            launch {
                dao.addToMyList(id)
            }
        }
    }

}