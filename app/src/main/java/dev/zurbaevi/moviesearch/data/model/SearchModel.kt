package dev.zurbaevi.moviesearch.data.model

import com.google.gson.annotations.SerializedName

data class SearchModel(

    @SerializedName("Search")
    var movieEntityList: MutableList<MovieModel>? = null,

    @SerializedName("totalResults")
    var totalResults: String? = null,

    @SerializedName("Response")
    var response: String? = null,
)