package dev.zurbaevi.moviesearch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("release_date")
    val releaseDate: String,
) : Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}