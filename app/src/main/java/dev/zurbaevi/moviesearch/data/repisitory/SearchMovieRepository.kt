package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchMovieRepository @Inject constructor(private val apiService: ApiService) {

    fun searchByName(query: String): Single<SearchModel> {
        return apiService.searchByName(query)
    }

}
