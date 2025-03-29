package com.capstone.submissionexpertone.presentation.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.submissionexpertone.core.data.Resource
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieUseCase: MovieUseCase
) : ViewModel() {

    val movies: LiveData<com.capstone.submissionexpertone.core.data.Resource<List<Movie>>> =
        movieUseCase.getPopularMovies().asLiveData()
}