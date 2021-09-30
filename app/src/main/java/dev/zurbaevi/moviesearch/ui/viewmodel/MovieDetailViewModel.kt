package dev.zurbaevi.moviesearch.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.zurbaevi.moviesearch.data.model.MovieDetailModel
import dev.zurbaevi.moviesearch.data.repisitory.MovieRepository
import dev.zurbaevi.moviesearch.utils.Resource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _movieDetailData = MutableLiveData<Resource<MovieDetailModel>>()
    val movieDetailData: LiveData<Resource<MovieDetailModel>>
        get() = _movieDetailData

    fun searchById(query: String) {
        _movieDetailData.postValue(Resource.loading(null))
        compositeDisposable.add(
            movieRepository.searchById(query)
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