package dev.zurbaevi.moviesearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import dev.zurbaevi.moviesearch.data.repisitory.MovieDetailRepository
import dev.zurbaevi.moviesearch.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel(private val detailMovieDetailRepository: MovieDetailRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _movieDetailData = MutableLiveData<Resource<MovieDetailModel>>()
    val movieDetailData: LiveData<Resource<MovieDetailModel>>
        get() = _movieDetailData

    fun searchById(query: String) {
        _movieDetailData.postValue(Resource.loading(null))
        compositeDisposable.add(
            detailMovieDetailRepository.searchById(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _movieDetailData.postValue(Resource.success(it))
                }, {
                    _movieDetailData.postValue(
                        Resource.error(
                            "Something went wrong!",
                            null
                        )
                    )
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}