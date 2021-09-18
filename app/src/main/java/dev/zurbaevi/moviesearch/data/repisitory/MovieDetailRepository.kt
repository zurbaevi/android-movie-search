package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.Observable

class MovieDetailRepository(private val apiService: ApiService) {

    fun searchById(query: String): Observable<MovieDetailModel> {
        return apiService.searchById(query)
    }

}