package br.com.alura.anyflix.model

import br.com.alura.anyflix.database.entities.MovieEntity
import br.com.alura.anyflix.network.MovieRequest
import java.util.UUID

data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val year: Int,
    val plot: String,
    val image: String? = null,
    val inMyList: Boolean = false
)

fun Movie.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    year = year,
    plot = plot,
    image = image,
    inMyList = inMyList
)

fun Movie.toMovieRequest() = MovieRequest(
    title = title,
    image = image
)