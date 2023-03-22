package br.com.alura.anyflix.network

import br.com.alura.anyflix.database.entities.MovieEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

data class MovieResponse(
    val id: String,
    val title: String,
    val image: String?,
    val year: Int,
    val plot: String,
    val inMyList: Boolean
)

data class MovieRequest(
    val title: String,
    val image: String?,
    val year: Int,
    val plot: String,
    val inMyList: Boolean
)

fun MovieResponse.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        image = image,
        year = year,
        plot = plot,
        inMyList = inMyList
    )
}

interface MovieService {

    @GET("movies")
    suspend fun findAll(): List<MovieResponse>

    @GET("movies/myList")
    suspend fun findMyList(): List<MovieResponse>

    @GET("movies/{id}")
    suspend fun findMovieById(@Path("id") id: String): MovieResponse

    @PUT("movies/addToMyList/{id}")
    suspend fun addToMyList(@Path("id") id: String): Response<Void>

    @PUT("movies/removeFromMyList/{id}")
    suspend fun removeFromMyList(@Path("id") id: String): Response<Void>

    @PUT("movies/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body request: MovieRequest
    ) : MovieResponse

}