package br.com.alura.anyflix.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class MovieRequest(
    val title: String,
    val image: String?
)

data class MovieResponse(
    val id: String,
    val title: String,
    val image: String
)

private const val MOVIES = "movies"

interface MoviesService {

    @GET(MOVIES)
    suspend fun findAll(): List<MovieResponse>

    @POST(MOVIES)
    suspend fun save(@Body request: MovieRequest)

    @PUT("$MOVIES/addToMyList/{id}")
    suspend fun addToMyList(@Path("id") id: String): Response<Any>

    @PUT("$MOVIES/removeFromMyList/{id}")
    suspend fun removeFromMyList(@Path("id") id: String): Response<Any>

    @GET("$MOVIES/{id}")
    fun findMovieById(@Path("id") id: String)

}