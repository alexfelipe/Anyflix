package br.com.alura.anyflix.sampleData

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.alura.anyflix.model.Movie
import kotlin.random.Random

val randomImage
    get() = "https://picsum.photos/${Random.nextInt(1380, 1920)}/${
        Random.nextInt(
            720,
            1080
        )
    }"

val randomTitle
    get() = LoremIpsum(Random.nextInt(1, 3)).values
        .first().toString()
val randomYear
    get() =
        Random.nextInt(1980, 2023)
val randomPlot
    get() = LoremIpsum(Random.nextInt(5, 10)).values
        .first().toString()


val sampleMovies = List(15) {
    Movie(
        title = randomTitle,
        image = randomImage,
        year = randomYear,
        plot = randomPlot
    )
}

val sampleMovieSections = mapOf(
    "Em alta" to sampleMovies.shuffled().take(7),
    "Novidades" to sampleMovies.shuffled().take(7),
    "Continue assistindo" to sampleMovies.shuffled().take(7)
)