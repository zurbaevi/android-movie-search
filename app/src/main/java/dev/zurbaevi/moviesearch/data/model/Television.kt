package dev.zurbaevi.moviesearch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Television(
    val id: Int,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val name: String
) : Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}