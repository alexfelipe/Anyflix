package br.com.alura.anyflix.network

import retrofit2.http.GET

data class MovieResponse(
    val title: String,
    val image: String?,
    val year: Int,
    val plot: String
)

interface MovieService {

    @GET("movies")
    suspend fun findAll(): List<MovieResponse>

}