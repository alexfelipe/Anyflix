package br.com.alura.anyflix.repositories

import android.util.Log
import br.com.alura.anyflix.database.dao.MovieDao
import br.com.alura.anyflix.database.entities.toMovie
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
                Log.e(TAG, "findSections: falha ao conectar na API", e)
            } catch (e: HttpException) {
                Log.e(TAG, "myList: não encontrou filmes na minha lista", e)
            }
        }
        return dao.myList()
            .map { it.map { entity -> entity.toMovie() } }
    }

    fun removeFromMyList(id: String) {
        TODO("Not yet implemented")
    }

    private fun createSections(movies: List<Movie>) = mapOf(
        "Em alta" to movies.shuffled().take(7),
        "Novidades" to movies.shuffled().take(7),
        "Continue assistindo" to movies.shuffled().take(7)
    )

}