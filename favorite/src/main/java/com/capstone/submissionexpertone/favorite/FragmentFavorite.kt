package com.capstone.submissionexpertone.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.submissionexpertone.MovieAdapter
import com.capstone.submissionexpertone.detail.DetailMovieActivity
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.core.domain.usecase.MovieUseCase
import com.capstone.submissionexpertone.favorite.databinding.FragmentFavoriteBinding
import com.capstone.submissionexpertone.favorite.di.FavoriteComponent
import com.capstone.submissionexpertone.favorite.di.FavoriteViewModelFactory
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var movieUseCase: MovieUseCase

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _movieAdapter: MovieAdapter? = null
    private val movieAdapter get() = _movieAdapter!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FavoriteComponent.create(context).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        setupRecyclerView()
        observeFavoriteMovies()
    }

    private fun setupRecyclerView() {
        _movieAdapter = MovieAdapter { movie ->
            navigateToDetail(movie)
        }

        binding.rvFavoriteMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = _movieAdapter
        }
    }

    private fun observeFavoriteMovies() {
        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNotEmpty()) {
                binding.tvNoData.visibility = View.GONE
                binding.rvFavoriteMovies.visibility = View.VISIBLE
                movieAdapter.submitList(movies)
            } else {
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvFavoriteMovies.visibility = View.GONE
            }
        }
    }

    private fun navigateToDetail(movie: Movie) {
        val intent = Intent(requireContext(), DetailMovieActivity::class.java).apply {
            putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, movie.id)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        binding.rvFavoriteMovies.adapter = null

        _movieAdapter?.clearListener()
        _movieAdapter = null

        super.onDestroyView()
        _binding = null
    }
}