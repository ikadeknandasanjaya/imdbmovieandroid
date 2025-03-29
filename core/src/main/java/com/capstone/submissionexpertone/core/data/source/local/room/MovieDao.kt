package com.capstone.submissionexpertone.core.data.source.local.room


import androidx.room.*
import com.capstone.submissionexpertone.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :movieId AND is_favorite = 1)")
    fun isFavorite(movieId: Int): Flow<Boolean>
}