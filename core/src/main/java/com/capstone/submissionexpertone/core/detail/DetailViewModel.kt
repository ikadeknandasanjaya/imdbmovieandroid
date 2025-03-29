package com.capstone.submissionexpertone.core.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.submissionexpertone.core.data.Resource
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val movieId = MutableLiveData<Int>()

    val currentMovie = MutableLiveData<Movie>()
    val isFavoriteStatus = MutableLiveData<Boolean>()

    fun getMovieDetail(id: Int): LiveData<com.capstone.submissionexpertone.core.data.Resource<Movie>> {
        movieId.value = id
        return movieUseCase.getMovieDetail(id).asLiveData().also { liveData ->
            liveData.observeForever { resource ->
                if (resource is com.capstone.submissionexpertone.core.data.Resource.Success) {
                    resource.data?.let { movie ->
                        currentMovie.value = movie
                    }
                }
            }
        }
    }

    fun isFavorite(id: Int): LiveData<Boolean> {
        return movieUseCase.isFavorite(id).asLiveData().also { liveData ->
            liveData.observeForever { isFavorite ->
                isFavoriteStatus.value = isFavorite
            }
        }
    }

    fun setFavoriteMovie(movie: Movie, state: Boolean) {
        movieUseCase.setFavoriteMovie(movie, state)
    }
}