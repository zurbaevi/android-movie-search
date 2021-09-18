package dev.zurbaevi.moviesearch.data.local

import androidx.room.*
import dev.zurbaevi.moviesearch.data.model.MovieModel
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(movie: MovieModel): Completable

    @Update
    fun update(movie: MovieModel)

    @Delete
    fun remove(movie: MovieModel)

    @Query("SELECT * FROM favorites WHERE id = :id")
    fun getById(id: String): Flowable<MovieModel>

    @Query("SELECT * FROM favorites")
    fun getAll(): Flowable<List<MovieModel>>

}