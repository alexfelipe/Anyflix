package br.com.alura.anyflix.sampleData

import br.com.alura.anyflix.model.Movie
import kotlin.random.Random

val randomImage
    get() = "https://picsum.photos/${Random.nextInt(1380, 1920)}/${
        Random.nextInt(
            720,
            1080
        )
    }"

val sampleMovies = listOf(
    Movie(
        image = randomImage
    ),
    Movie(
        image = randomImage
    ),
    Movie(
        image = randomImage
    ),
    Movie(
        image = randomImage
    ),
    Movie(
        image = randomImage
    ),
)