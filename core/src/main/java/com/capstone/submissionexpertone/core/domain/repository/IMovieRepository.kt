package com.capstone.submissionexpertone.core.domain.repository


import com.capstone.submissionexpertone.core.data.Resource
import com.capstone.submissionexpertone.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getPopularMovies(): Flow<Resource<List<Movie>>>
    fun getMovieDetail(movieId: Int): Flow<Resource<Movie>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
    fun isFavorite(movieId: Int): Flow<Boolean>
}