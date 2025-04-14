package com.capstone.submissionexpertone.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
      movieUseCase: MovieUseCase
) : ViewModel() {
    val favoriteMovies: LiveData<List<Movie>> = movieUseCase.getFavoriteMovies().asLiveData()
}