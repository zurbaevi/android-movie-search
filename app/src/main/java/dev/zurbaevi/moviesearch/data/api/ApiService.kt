package dev.zurbaevi.moviesearch.data.api

import dev.zurbaevi.moviesearch.BuildConfig
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(".")
    fun searchById(
        @Query("i", encoded = true) i: String,
        @Query("apikey", encoded = true) apiKey: String = BuildConfig.API_KEY
    ): Single<MovieDetailModel>

    @GET(".")
    fun searchByName(
        @Query("s", encoded = true) s: String,
        @Query("apikey", encoded = true) apiKey: String = BuildConfig.API_KEY
    ): Single<SearchModel>

}