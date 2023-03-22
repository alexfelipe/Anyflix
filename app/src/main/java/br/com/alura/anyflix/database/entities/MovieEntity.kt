package br.com.alura.anyflix.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alura.anyflix.model.Movie
import br.com.alura.anyflix.network.MovieRequest
import java.util.UUID

@Entity(tableName = "movies")
class MovieEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val year: Int,
    val plot: String,
    val image: String? = null,
    val inMyList: Boolean = false,
    val synchronized: Boolean = false
) {
}

fun MovieEntity.toMovieRequest() = MovieRequest(
    title = title,
    image = image,
    year = year,
    plot = plot,
    inMyList = inMyList
)

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    year = year,
    plot = plot,
    image = image,
    inMyList = inMyList
)
