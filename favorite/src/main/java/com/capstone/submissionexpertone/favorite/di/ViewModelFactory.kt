package com.capstone.submissionexpertone.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import com.capstone.submissionexpertone.favorite.FavoriteViewModel
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}