package com.capstone.submissionexpertone.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("vote_count")
    val voteCount: Int
)

data class MovieListResponse(
    @field:SerializedName("results")
    val results: List<MovieResponse>
)