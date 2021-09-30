package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun searchById(query: String): Single<MovieDetailModel> {
        return apiService.searchById(query)
    }

    fun searchByName(query: String): Single<SearchModel> {
        return apiService.searchByName(query)
    }
}