package com.capstone.submissionexpertone.detail


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.capstone.submissionexpertone.core.R
import com.capstone.submissionexpertone.core.data.Resource
import com.capstone.submissionexpertone.core.databinding.ActivityDetailMovieBinding
import com.capstone.submissionexpertone.core.domain.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMovieBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)

        observeMovieDetail()
        observeFavoriteStatus()
        setupFavoriteButton()
    }

    private fun observeMovieDetail() {
        detailViewModel.getMovieDetail(movieId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { movie ->
                        populateMovieDetails(movie)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeFavoriteStatus() {
        detailViewModel.isFavorite(movieId).observe(this) { isFavorite ->
            setFavoriteButtonState(isFavorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.fabFavorite.setOnClickListener {
            detailViewModel.currentMovie.value?.let { movie ->
                val isFavorite = detailViewModel.isFavoriteStatus.value ?: false
                detailViewModel.setFavoriteMovie(movie, !isFavorite)

                val message = if (!isFavorite) {
                    getString(R.string.added_to_favorite)
                } else {
                    getString(R.string.removed_from_favorite)
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateMovieDetails(movie: Movie) {
        with(binding) {
            collapsingToolbar.title = movie.title
            tvTitle.text = movie.title
            tvReleaseDate.text = getString(
                com.capstone.submissionexpertone.R.string.release_date_format,
                movie.releaseDate ?: getString(com.capstone.submissionexpertone.R.string.unknown_release_date)
            )

            tvRating.text = getString(
                com.capstone.submissionexpertone.R.string.rating_format,
                movie.voteAverage.toString(),
                movie.voteCount
            )

            tvOverview.text = movie.overview

            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
                .into(imgBackdrop)

            Glide.with(this@DetailMovieActivity)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(imgPoster)
        }
    }

    private fun setFavoriteButtonState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_favorite)
            )
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}