package com.example.mymovieapp.screen_movie_details.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Status
import com.example.mymovieapp.app.utils.Cons.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Cons.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.ActivityMovieDetailsBinding
import com.example.mymovieapp.screen_movie_details.domain.models.MovieDetails
import com.example.mymovieapp.screen_movie_details.presentation.adapter.MovieAdapter
import com.example.mymovieapp.screen_movie_details.presentation.adapter.TrailerAdapter
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieItemOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsMovieActivity : AppCompatActivity(), MovieItemOnClickListener {

    private val binding: ActivityMovieDetailsBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsMovieViewModel by viewModels()

    private val trailerAdapter: TrailerAdapter by lazy(LazyThreadSafetyMode.NONE) { TrailerAdapter() }

    private val similarAdapter: MovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieAdapter(actionListener = this)
    }
    private var movie: MovieDetails? = null
    private var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.videoRecyclerView.adapter = trailerAdapter
        binding.similarRecyclerView.adapter = similarAdapter

        val id = intent.extras?.get(MOVIE_ID_KEY) as Int
        viewModel.setMovieId(id = id)

        viewModel.isFavorite.observe(this) { boolean -> isFavorite = boolean }

        viewModel.resource.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    uiVisibility(visibility = false)
                }
                Status.SUCCESS -> {
                    movie = resource.data!!.movie
                    bindUI(movie = movie!!)
                    trailerAdapter.trailerList = resource.data.trailerList
                    similarAdapter.movieList = resource.data.movieSimilar
                    uiVisibility(visibility = true)
                }
                Status.ERROR -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    uiVisibility(visibility = true)
                }
            }

        }

        binding.favoriteBtn.setOnClickListener {
            if (isFavorite) {
                isFavorite = false
                binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                viewModel.deleteMovieFavorite(movie = movie!!)
            } else {
                isFavorite = true
                binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModel.addMovieFavorite(movie = movie!!)
            }
        }

    }

    private fun bindUI(movie: MovieDetails) {
        binding.apply {
            if (isFavorite) favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            setSupportActionBar(toolbar)
            supportActionBar?.title = movie.title
            originalLanguage.text = movie.originalLanguage
            title.text = movie.title
            popularity.text = movie.popularity.toString()
            voteCount.text = movie.voteCount
            voteAverage.rating = movie.rating.toFloat()
            originalTitle.text = movie.originalTitle
            relaseDate.text = movie.releaseDate
            overivew.text = movie.overview
        }
        val moviePosterURL = POSTER_BASE_URL + movie.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.imageView)

    }

    private fun uiVisibility(visibility: Boolean) {
        if (!visibility) {
            binding.needScroll.visibility = View.GONE
            binding.collapsingToolBar.visibility = View.GONE
            binding.progressDialog.visibility = View.VISIBLE
        } else {
            binding.needScroll.visibility = View.VISIBLE
            binding.collapsingToolBar.visibility = View.VISIBLE
            binding.progressDialog.visibility = View.GONE
        }

    }

    override fun showDetailsMovie(id: Int) {
        val intent = Intent(this, DetailsMovieActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY, id)
        startActivity(intent)
    }
}