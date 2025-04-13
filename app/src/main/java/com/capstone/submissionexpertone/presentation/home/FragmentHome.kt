package com.capstone.submissionexpertone.presentation.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.submissionexpertone.MovieAdapter
import com.capstone.submissionexpertone.detail.DetailMovieActivity
import com.capstone.submissionexpertone.core.domain.model.Movie
import com.capstone.submissionexpertone.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private var _movieAdapter: MovieAdapter? = null
    private val movieAdapter get() = _movieAdapter!!
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeMovies()
    }

    private fun setupRecyclerView() {
        _movieAdapter = MovieAdapter { movie ->
            navigateToDetail(movie)
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = _movieAdapter
        }
    }

    private fun observeMovies() {
        homeViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is com.capstone.submissionexpertone.core.data.Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is com.capstone.submissionexpertone.core.data.Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    resource.data?.let { movies ->
                        if (movies.isNotEmpty()) {
                            movieAdapter.submitList(movies)
                        }
                    }
                }
                is com.capstone.submissionexpertone.core.data.Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = resource.message
                }
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
        binding.rvMovies.adapter = null

        _movieAdapter?.clearListener()
        _movieAdapter = null

        super.onDestroyView()
        _binding = null
    }
}