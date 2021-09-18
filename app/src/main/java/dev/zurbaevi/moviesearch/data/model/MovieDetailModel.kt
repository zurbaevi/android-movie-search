package dev.zurbaevi.moviesearch.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel(

    @SerializedName("Title")
    var title: String = "",

    @SerializedName("Genre")
    var genre: String = "",

    @SerializedName("imdbRating")
    var rating: String = "",

    @SerializedName("Runtime")
    var time: String = "",

    @SerializedName("Released")
    var released: String = "",

    @SerializedName("Poster")
    var poster: String = "",

    @SerializedName("Plot")
    var overview: String = "",

    @SerializedName("Type")
    var type: String = "",

    @SerializedName("BoxOffice")
    var boxOffice: String = "",

    @SerializedName("Awards")
    var awards: String = ""

)
