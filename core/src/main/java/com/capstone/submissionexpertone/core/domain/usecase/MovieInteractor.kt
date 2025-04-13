package com.capstone.submissionexpertone.core.domain.usecase



import com.capstone.submissionexpertone.core.data.Resource
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MovieInteractor @Inject constructor(

    private val movieRepository: IMovieRepository
) : MovieUseCase {

    override fun getPopularMovies(): Flow<Resource<List<Movie>>> =
        movieRepository.getPopularMovies()

    override fun getMovieDetail(movieId: Int): Flow<Resource<Movie>> =
        movieRepository.getMovieDetail(movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        movieRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

    override fun isFavorite(movieId: Int): Flow<Boolean> =
        movieRepository.isFavorite(movieId)
}