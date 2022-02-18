package com.example.mymovieapp.app.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.movie_details_screen.domain.model.Trailer
import com.example.mymovieapp.databinding.TrilerItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener


class TrailerAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var trailerList: List<Trailer> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val productsItems: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.triler_item, parent, false)
        return FruitViewHolder(productsItems)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fruitHolder = holder as FruitViewHolder
        fruitHolder.bind(trailerList[position])

    }

    inner class FruitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = TrilerItemBinding.bind(itemView)
        @SuppressLint("NotifyDataSetChanged")
        fun bind(trailer: Trailer) {
            binding.apply {
                youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
                    override fun onApiChange(youTubePlayer: YouTubePlayer) {
                    }

                    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError,
                    ) {
                    }

                    override fun onPlaybackQualityChange(
                        youTubePlayer: YouTubePlayer,
                        playbackQuality: PlayerConstants.PlaybackQuality,
                    ) {
                    }

                    override fun onPlaybackRateChange(
                        youTubePlayer: YouTubePlayer,
                        playbackRate: PlayerConstants.PlaybackRate,
                    ) {
                    }

                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(trailer.key, 0F)

                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState,
                    ) {
                    }

                    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                    }

                    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {

                    }

                    override fun onVideoLoadedFraction(
                        youTubePlayer: YouTubePlayer,
                        loadedFraction: Float,
                    ) {
                    }

                })


            }
        }
    }


    override fun getItemCount(): Int = trailerList.size


}
