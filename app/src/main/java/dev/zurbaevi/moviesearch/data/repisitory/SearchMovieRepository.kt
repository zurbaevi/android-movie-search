package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.local.MovieDao
import dev.zurbaevi.moviesearch.data.model.MovieModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.Completable
import io.reactivex.Observable

class SearchMovieRepository(private val apiService: ApiService) {

    fun searchByName(query: String): Observable<SearchModel> {
        return apiService.searchByName(query)
    }

}
