package dev.zurbaevi.moviesearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import dev.zurbaevi.moviesearch.data.repisitory.SearchMovieRepository
import dev.zurbaevi.moviesearch.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchViewModel(private val searchMovieRepository: SearchMovieRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _movieListData = MutableLiveData<Resource<SearchModel>>()
    val movieListData: LiveData<Resource<SearchModel>>
        get() = _movieListData

    fun searchByName(query: String) {
        _movieListData.postValue(Resource.loading(null))
        compositeDisposable.add(
            searchMovieRepository.searchByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.response.toString() == "False") {
                        _movieListData.postValue(
                            Resource.error("Sorry, no movie found, try another title!", null)
                        )
                    } else {
                        _movieListData.postValue(Resource.success(it))
                    }
                }, {
                    _movieListData.postValue(Resource.error("Something went wrong!", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}