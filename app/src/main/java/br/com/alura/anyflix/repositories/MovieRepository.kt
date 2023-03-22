package br.com.alura.anyflix.repositories

import android.util.Log
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.network.MovieService
import br.com.alura.anyflix.network.toMovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

private const val TAG = "MovieRepository"

class MovieRepository @Inject constructor(
    private val dao: MovieDao,
    private val service: MovieService
) {

    data class Result<T>(
        val data: T? = null,
        val exception: Exception? = null
    )

    suspend fun findSections(): Flow<Result<Map<String, List<Movie>>>> {
        val flow = MutableStateFlow<Result<Map<String, List<Movie>>>>(Result())
        val scope = CoroutineScope(coroutineContext)
        scope.launch {
            try {
                val response = service.findAll()
                val movies = response.map {
                    it.toMovieEntity()
                }
                dao.saveAll(*movies.toTypedArray())
            } catch (e: ConnectException) {
                flow.update {
                    it.copy(exception = e)
                }
                Log.e(TAG, "findSections: falha ao conectar na API", e)
            } catch (e: SocketTimeoutException) {
                flow.update {
                    it.copy(exception = e)
                }
                Log.e(TAG, "findSections: falha ao conectar na API", e)
            }
        }
        scope.launch {
            dao.findAll()
                .map {
                    it.map { entity ->
                        entity.toMovie()
                    }
                }.collect { movies ->
                    if (movies.isEmpty()) {
                        flow.update {
                            it.copy(data = emptyMap())
                        }
                    } else {
                        flow.update {
                            it.copy(data = createSections(movies))
                        }
                    }
                }
        }
        return flow
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