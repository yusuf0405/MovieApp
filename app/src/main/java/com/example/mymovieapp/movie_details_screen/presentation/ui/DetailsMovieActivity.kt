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
import com.example.mymovieapp.R
import com.example.mymovieapp.movie_details_screen.presentation.adapter.TrailerAdapter
import com.example.mymovieapp.movie_screen.presentation.adapter.ItemOnClickListener
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.ActivityMovieDetailsBinding
import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.movie_details_screen.domain.models.MovieDetails
import com.example.mymovieapp.movie_details_screen.presentation.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailsMovieActivity : AppCompatActivity() {
    private val binding: ActivityMovieDetailsBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsMovieViewModel by viewModels()
    private val trailerAdapter = TrailerAdapter()
    private var favoriteMovies: List<FavoriteMovie>? = null
    private var movie: MovieDetails? = null
    private var isFavorite = false

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
        recyclerBind()
        val id = intent.extras?.get(MOVIE_ID_KEY) as Int

        GlobalScope.launch(Dispatchers.Main) {
            favoriteMovies = viewModel.allFavoriteMovies()
            viewModel.getMovieDetails(id = id)
            viewModel.getMovieTrailer(id = id)
            viewModel.getSimilarMovie(id = id)
            uiVisibility(false)

        }
        viewModel.movieOfList.observe(this) {
            movie = it.body()!!
            bindUI(movie = movie!!, favoriteMovies!!)
            val toolbar = binding.toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = it.body()!!.title


        }
        viewModel.trailerResponse.observe(this) {
            trailerAdapter.trailerList = it.body()!!.trailerList
        }
        viewModel.movieSimilarResponse.observe(this) {
            similarMovieAdapter.movieList = it.body()!!.movieList
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

    @SuppressLint("SetTextI18n")
    private fun bindUI(movie: MovieDetails, favoriteMovies: List<FavoriteMovie>) {
        favoriteMovies.forEach {
            if (it.id == movie.id) {
                isFavorite = true
            }
        }
        binding.apply {
            if (isFavorite) {
                favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
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

    private fun recyclerBind() {
        binding.videoRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.videoRecyclerView.adapter = trailerAdapter

        binding.similarRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.similarRecyclerView.adapter = similarMovieAdapter
    }

    private fun uiVisibility(visible: Boolean) {
        if (!visible) {
            binding.viewConstraint.visibility = View.GONE
            binding.favoriteBtn.visibility = View.GONE
            binding.similarRecyclerView.visibility= View.GONE
            binding.videoRecyclerView.visibility= View.GONE
            binding.progressDialog.visibility = View.VISIBLE
        } else {
            binding.viewConstraint.visibility = View.VISIBLE
            binding.favoriteBtn.visibility = View.VISIBLE
            binding.similarRecyclerView.visibility= View.VISIBLE
            binding.videoRecyclerView.visibility= View.VISIBLE
            binding.progressDialog.visibility = View.GONE
        }

    }
}