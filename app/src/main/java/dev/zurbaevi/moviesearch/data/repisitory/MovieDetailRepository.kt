package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import io.reactivex.rxjava3.core.Single

class MovieDetailRepository(private val apiService: ApiService) {

    fun searchById(query: String): Single<MovieDetailModel> {
        return apiService.searchById(query)
    }

}