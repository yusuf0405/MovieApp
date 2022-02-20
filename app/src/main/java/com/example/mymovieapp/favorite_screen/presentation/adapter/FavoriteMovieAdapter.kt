package com.example.mymovieapp.favorite_screen.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils
import com.example.mymovieapp.databinding.FavMovieItemBinding
import com.example.mymovieapp.favorite_screen.domain.model.FavoriteMovie
import com.squareup.picasso.Picasso

class FavMoviesDiffCallBack(
    private val oldNotes: List<FavoriteMovie>,
    private val newNotes: List<FavoriteMovie>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNotes.size

    override fun getNewListSize(): Int = newNotes.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote.id == newNote.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNotes[oldItemPosition]
        val newNote = newNotes[newItemPosition]
        return oldNote == newNote
    }

}

interface FavMovieItemOnClick {
    fun deleteMovie(movie: FavoriteMovie)
}

class FavoriteMovieAdapter(private val actionListener: FavMovieItemOnClick) :
    RecyclerView.Adapter<FavoriteMovieAdapter.MovieViewHolder>() {
    var favoriteMovieList: List<FavoriteMovie> = emptyList()
        set(newValue) {
            val diffCallBack = FavMoviesDiffCallBack(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class MovieViewHolder(private val binding: FavMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovie) {
            val rating = movie.rating * 10
            binding.apply {
                cvMovieReleaseDate.text = movie.releaseDate
                cvMovieTitle.text = movie.title
                val moviePosterURL = Utils.POSTER_BASE_URL + movie.posterPath
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
                deleteBtn.setOnClickListener {
                    actionListener.deleteMovie(movie = movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.fav_movie_item, parent, false)
        val binding = FavMovieItemBinding.bind(layoutInflater)
        return MovieViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(favoriteMovieList[position])
    }

    override fun getItemCount(): Int = favoriteMovieList.size

}