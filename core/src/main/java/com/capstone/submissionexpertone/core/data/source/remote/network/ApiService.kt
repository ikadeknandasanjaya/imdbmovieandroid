package com.capstone.submissionexpertone.core.data.source.remote.network


import com.capstone.submissionexpertone.core.BuildConfig
import com.capstone.submissionexpertone.core.data.source.remote.response.MovieListResponse
import com.capstone.submissionexpertone.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse
}