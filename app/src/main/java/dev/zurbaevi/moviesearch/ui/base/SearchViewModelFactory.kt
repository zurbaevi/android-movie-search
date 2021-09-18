package dev.zurbaevi.moviesearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.repisitory.SearchMovieRepository
import dev.zurbaevi.moviesearch.ui.main.viewmodel.SearchViewModel

class SearchViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(SearchMovieRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}