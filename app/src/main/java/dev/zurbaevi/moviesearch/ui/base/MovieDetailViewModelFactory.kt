package dev.zurbaevi.moviesearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.repisitory.MovieDetailRepository
import dev.zurbaevi.moviesearch.ui.main.viewmodel.MovieDetailViewModel

class MovieDetailViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(MovieDetailRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}