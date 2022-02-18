package com.example.mymovieapp.movie_details_screen.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovieapp.app.adapter.TrailerAdapter
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.app.movie.ItemOnClickListener
import com.example.mymovieapp.databinding.ActivityMovieDetailsBinding
import com.example.mymovieapp.movie_details_screen.domain.model.MovieDetails
import com.example.mymovieapp.movie_details_screen.presentation.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsMovieActivity : AppCompatActivity() {
    private val binding: ActivityMovieDetailsBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsMovieViewModel by viewModels()
    private val trailerAdapter = TrailerAdapter()


    private val similarMovieAdapter = MovieAdapter(object : ItemOnClickListener {
        override fun showDetailsMovie(id: Int) {
            val intent = Intent(this@DetailsMovieActivity, DetailsMovieActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, id)
            this@DetailsMovieActivity.startActivity(intent)
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = (intent?.extras?.get(MOVIE_ID_KEY) as Int)

        binding.videoRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.videoRecyclerView.adapter = trailerAdapter

        binding.similarRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.similarRecyclerView.adapter = similarMovieAdapter

        if (id != 0) {
            binding.viewConstraint.visibility = View.GONE
            binding.progressDialog.visibility = View.VISIBLE
            viewModel.getMovieDetails(id = id)
            viewModel.getMovieTrailer(id = id)
            viewModel.getSimilarMovie(id = id)

            viewModel.movieOfList.observe(this) {
                bindUI(movie = it.body()!!)
                val toolbar = binding.toolbar
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = it.body()!!.title
                binding.viewConstraint.visibility = View.VISIBLE
                binding.progressDialog.visibility = View.GONE

            }
            viewModel.movieListTrailerResponse.observe(this) {
                trailerAdapter.trailerList = it.body()!!.trailerList
            }
            viewModel.movieSimilarResponse.observe(this) {
                similarMovieAdapter.movieList = it.body()!!.movieList
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(movie: MovieDetails) {
        binding.apply {
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
}