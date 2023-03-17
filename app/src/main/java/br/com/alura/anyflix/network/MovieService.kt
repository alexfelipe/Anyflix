package br.com.alura.anyflix.network

import br.com.alura.anyflix.model.Movie
import retrofit2.http.GET

data class MovieResponse(
    val id: String,
    val title: String,
    val image: String?,
    val year: Int,
    val plot: String
)

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        image = image,
        year = year,
        plot = plot
    )
}

interface MovieService {

    @GET("movies")
    suspend fun findAll(): List<MovieResponse>

}