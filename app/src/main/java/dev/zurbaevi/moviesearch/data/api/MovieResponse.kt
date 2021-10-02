package dev.zurbaevi.moviesearch.data.api

import dev.zurbaevi.moviesearch.data.model.Movie

data class MovieResponse(
    val results: List<Movie>,
)