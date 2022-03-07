package com.example.mymovieapp.movie_screen.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.MovieItemBinding
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject


interface MovieItemOnClickListener {
    fun showDetailsMovie(id: Int)
}

@DelicateCoroutinesApi
class MovieAdapter @Inject constructor(
    private val actionListener: MovieItemOnClickListener,
) : PagingDataAdapter<Movie, MovieAdapter.HomeNewsViewHolder>(MovieDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewsViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        val binding = MovieItemBinding.bind(inflater)
        return HomeNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeNewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    @DelicateCoroutinesApi
    inner class HomeNewsViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
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
            }
            itemView.setOnClickListener {
                actionListener.showDetailsMovie(id = movie.id)
            }

        }
    }
}


private object MovieDiffItemCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title && oldItem.id == newItem.id
    }
}