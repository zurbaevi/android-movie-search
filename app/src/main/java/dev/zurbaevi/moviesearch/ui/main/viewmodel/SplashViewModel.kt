package dev.zurbaevi.moviesearch.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SplashViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _liveData: MutableLiveData<Boolean> = MutableLiveData()
    val liveData: LiveData<Boolean>
        get() = _liveData

    fun initSplashScreen() {
        compositeDisposable.add(
            Observable.timer(1, TimeUnit.SECONDS)
                .subscribe {
                    updateLiveData()
                })
    }

    private fun updateLiveData() {
        _liveData.postValue(true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}