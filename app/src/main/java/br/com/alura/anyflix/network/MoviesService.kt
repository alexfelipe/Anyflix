package br.com.alura.anyflix.network

import br.com.alura.anyflix.database.entities.MovieEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class MovieRequest(
    val title: String,
    val image: String?,
    val year: Int,
    val plot: String,
)

data class MovieResponse(
    val id: String,
    val title: String,
    val image: String,
    val year: Int,
    val plot: String,
    val inMyList: Boolean
)

fun MovieResponse.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    image = image,
    year = year,
    plot = plot,
    inMyList = inMyList
)

private const val MOVIES = "movies"

interface MoviesService {

    @GET(MOVIES)
    suspend fun findAll(): List<MovieResponse>

    @PUT("$MOVIES/addToMyList/{id}")
    suspend fun addToMyList(@Path("id") id: String): Response<Void>

    @PUT("$MOVIES/removeFromMyList/{id}")
    suspend fun removeFromMyList(@Path("id") id: String): Response<Void>

    @GET("$MOVIES/{id}")
    suspend fun findMovieById(@Path("id") id: String)

    @GET("$MOVIES/myList")
    suspend fun myList(): List<MovieResponse>

}