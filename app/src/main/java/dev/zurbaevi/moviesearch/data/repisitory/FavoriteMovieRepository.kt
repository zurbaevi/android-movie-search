package dev.zurbaevi.moviesearch.data.repisitory

import dev.zurbaevi.moviesearch.data.local.MovieDao
import dev.zurbaevi.moviesearch.data.model.MovieModel
import io.reactivex.Flowable

class FavoriteMovieRepository(private val movieDao: MovieDao) {

    fun save(movieModel: MovieModel) {
        movieDao.save(movieModel)
    }

    fun update(movieModel: MovieModel) {
        movieDao.update(movieModel)
    }

    fun remove(movieModel: MovieModel) {
        movieDao.remove(movieModel)
    }

    fun getById(id: String): Flowable<MovieModel> {
        return movieDao.getById(id)
    }

    fun getAll(): Flowable<List<MovieModel>> {
        return movieDao.getAll()
    }

}