package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.Single

class SearchMovieRepository(private val apiService: ApiService) {

    fun searchByName(query: String): Single<SearchModel> {
        return apiService.searchByName(query)
    }

}
