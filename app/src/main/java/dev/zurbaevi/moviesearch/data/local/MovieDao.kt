package dev.zurbaevi.moviesearch.data.local

import androidx.room.*
import dev.zurbaevi.moviesearch.data.model.MovieModel
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert
    fun save(movie: MovieModel)

    @Update
    fun update(movie: MovieModel)

    @Delete
    fun remove(movie: MovieModel)

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getById(id: String): Flowable<MovieModel>

    @Query("SELECT * FROM favorites")
    fun getAll(): Flowable<List<MovieModel>>

}