package com.example.mymovieapp.movie_details_screen.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.ActivityMovieDetailsBinding
import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.movie_details_screen.domain.models.MovieDetails
import com.example.mymovieapp.movie_details_screen.presentation.adapter.MovieAdapter
import com.example.mymovieapp.movie_details_screen.presentation.adapter.TrailerAdapter
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieItemOnClickListener
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
    private val similarAdapter: MovieAdapter by lazy(LazyThreadSafetyMode.NONE) { MovieAdapter(this) }


    private var favoriteMovies: List<FavoriteMovie>? = null
    private var movie: MovieDetails? = null
    private var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.videoRecyclerView.adapter = trailerAdapter
        binding.similarRecyclerView.adapter = similarAdapter

        val id = intent.extras?.get(MOVIE_ID_KEY) as Int
        viewModel.getMovieDetails(id = id)
        viewModel.getMovieTrailer(id = id)
        viewModel.getSimilarMovie(id = id)
        viewModel.allFavoriteMovies()
        uiVisibility(false)

        viewModel.movieOfList.observe(this) {
            movie = it.body()!!
            bindUI(movie = movie!!, favoriteMovies!!)
            supportActionBar?.title = it.body()!!.title
        }
        viewModel.movieFavList.observe(this) { favoriteMovies = it }

        viewModel.trailerResponse.observe(this) {
            trailerAdapter.trailerList = it.body()!!.trailerList
        }
        viewModel.movieSimilar.observe(this) {
            similarAdapter.movieList = it.body()!!.movieList
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

    private fun bindUI(movie: MovieDetails, favoriteMovies: List<FavoriteMovie>) {
        favoriteMovies.forEach { if (it.id == movie.id) isFavorite = true }
        binding.apply {
            if (isFavorite) favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            toolbar.title = movie.title
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
        uiVisibility(true)

    }

    private fun uiVisibility(visible: Boolean) {
        if (!visible) {
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