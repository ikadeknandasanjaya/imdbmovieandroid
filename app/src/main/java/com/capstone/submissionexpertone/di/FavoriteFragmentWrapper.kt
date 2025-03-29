package com.capstone.submissionexpertone.di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.submissionexpertone.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragmentWrapper : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wrapper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Coba load fragment dari dynamic feature
        val favoriteFragment = try {
            val fragmentClass = Class.forName("com.capstone.submissionexpertone.favorite.FavoriteFragment")
            fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            // Jika gagal, gunakan fragment kosong
            Fragment()
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, favoriteFragment)
            .commit()
    }
}