package dev.zurbaevi.moviesearch.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") position: Int,
    ): Single<MovieResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Single<MovieResponse>

    @GET("tv/popular")
    fun getTelevisionPopular(
        @Query("page") position: Int,
    ): Single<TelevisionResponse>

    @GET("search/tv")
    fun searchTelevision(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Single<TelevisionResponse>

}