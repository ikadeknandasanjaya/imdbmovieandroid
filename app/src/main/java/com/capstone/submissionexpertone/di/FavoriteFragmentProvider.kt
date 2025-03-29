package com.capstone.submissionexpertone.di


import androidx.fragment.app.Fragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteFragmentProvider @Inject constructor() {

    fun getFavoriteFragment(): Fragment {
        return try {
            val fragmentClass = Class.forName("com.capstone.submissionexpertone.favorite.FavoriteFragment")
            fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            Fragment()
        }
    }
}