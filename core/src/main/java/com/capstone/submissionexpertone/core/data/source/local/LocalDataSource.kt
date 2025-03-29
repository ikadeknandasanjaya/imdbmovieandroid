package com.capstone.submissionexpertone.core.data.source.local


import com.capstone.submissionexpertone.core.data.source.local.entity.MovieEntity
import com.capstone.submissionexpertone.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    fun getMovieById(movieId: Int): Flow<MovieEntity> = movieDao.getMovieById(movieId)

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun insertMovie(movie: MovieEntity) = movieDao.insertMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

    fun isFavorite(movieId: Int): Flow<Boolean> = movieDao.isFavorite(movieId)
}