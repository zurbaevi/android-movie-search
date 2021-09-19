package dev.zurbaevi.moviesearch.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(

    @SerializedName("imdbID")
    var imdbID: String = "",

    @SerializedName("Title")
    var title: String = "",

    @SerializedName("Year")
    var year: String = "",

    @SerializedName("Type")
    var type: String = "",

    @SerializedName("Poster")
    var poster: String = ""

)
