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

val sampleMovies = listOf(
    Movie(
        title = randomTitle,
        image = randomImage
    ),
    Movie(
        title = randomTitle,
        image = randomImage
    ),
    Movie(
        title = randomTitle,
        image = randomImage
    ),
    Movie(
        title = randomTitle,
        image = randomImage
    ),
    Movie(
        title = randomTitle,
        image = randomImage
    ),
)