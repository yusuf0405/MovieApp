package com.example.mymovieapp.movie_details_screen.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.MovieItemBinding
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieItemOnClickListener
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val actionListener: MovieItemOnClickListener) :
    RecyclerView.Adapter<MovieAdapter.SimilarViewHolder>() {

    var movieList = emptyList<Movie>()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()

        }

    inner class SimilarViewHolder(var binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val rating = movie.rating * 10
            binding.apply {
                cvMovieReleaseDate.text = movie.releaseDate
                cvMovieTitle.text = movie.title
                val moviePosterURL = POSTER_BASE_URL + movie.posterPath
                Picasso.get()
                    .load(moviePosterURL)
                    .resize(200, 200)
                    .into(cvIvMoviePoster)
                progressView.setProgress(rating.toInt())
                when {
                    rating >= 70 -> {
                        progressView.setProgressColorRes(R.color.green)
                    }
                    rating.toInt() in 41..69 -> {
                        progressView.setProgressColorRes(R.color.orange)
                    }
                    else -> {
                        progressView.setProgressColorRes(R.color.red)
                    }
                }
                itemView.setOnClickListener {
                    actionListener.showDetailsMovie(movie.id)
                }


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        val binding = MovieItemBinding.bind(inflater)
        return SimilarViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}