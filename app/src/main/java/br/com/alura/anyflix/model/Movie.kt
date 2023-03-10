package br.com.alura.anyflix.model

import java.util.UUID


data class Movie(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val image: String? = null
)