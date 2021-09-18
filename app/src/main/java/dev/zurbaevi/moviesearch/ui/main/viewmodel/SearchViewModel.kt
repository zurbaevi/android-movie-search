package dev.zurbaevi.moviesearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.zurbaevi.moviesearch.data.model.SearchModel
import dev.zurbaevi.moviesearch.data.repisitory.SearchMovieRepository
import dev.zurbaevi.moviesearch.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val searchMovieRepository: SearchMovieRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _movieListData = MutableLiveData<Resource<SearchModel>>()
    val movieListData: LiveData<Resource<SearchModel>>
        get() = _movieListData

//    private val _movieNotExist = MutableLiveData<Resource<Boolean>>()
//    val movieNotExist: LiveData<Resource<Boolean>>
//        get() = _movieNotExist
//
//    private val _responseControl = MutableLiveData<Resource<Boolean>>()
//    val responseControl: LiveData<Resource<Boolean>>
//        get() = _responseControl
//
//    private val _progress = MutableLiveData<Resource<Boolean>>()
//    val progress: LiveData<Resource<Boolean>>
//        get() = _progress

    fun searchByName(query: String) {
        compositeDisposable.add(
            searchMovieRepository.searchByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _movieListData.postValue(Resource.success(it))
                }, {
                    _movieListData.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}