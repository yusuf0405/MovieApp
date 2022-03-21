package com.example.mymovieapp.screen_person_details.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Status
import com.example.mymovieapp.app.utils.Cons
import com.example.mymovieapp.app.utils.Cons.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Cons.Companion.PERSON_ID_KEY
import com.example.mymovieapp.databinding.ActivityDetailsPersonBinding
import com.example.mymovieapp.screen_movie_details.presentation.adapter.MovieAdapter
import com.example.mymovieapp.screen_movie_details.presentation.ui.DetailsMovieActivity
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieItemOnClickListener
import com.example.mymovieapp.screen_person_details.domain.models.PersonDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class DetailsPersonActivity : AppCompatActivity(), MovieItemOnClickListener {

    private val binding: ActivityDetailsPersonBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityDetailsPersonBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsPersonViewModel by viewModels()

    private val adapter: MovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieAdapter(actionListener = this)
    }

    private var person: PersonDetails? = null
    private var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter

        val id = intent?.extras?.getInt(PERSON_ID_KEY)
        viewModel.setId(id = id!!)


        viewModel.isFavorite.observe(this) { isFavorite = it }
        viewModel.resource.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    uiVisibility(visibility = false)

                }
                Status.SUCCESS -> {
                    person = resource.data!!.person
                    bindUI(person!!)
                    adapter.movieList = resource.data.creditMovies
                    uiVisibility(visibility = true)
                }
                Status.ERROR -> {
                    uiVisibility(visibility = true)
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()

                }
            }

        }

        binding.tvFavoritePerson.setOnClickListener {
            if (isFavorite) {
                isFavorite = false
                binding.tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                viewModel.deletePersonFavorite(person = person!!)
            } else {
                isFavorite = true
                binding.tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModel.addPersonFavorite(person = person!!)
            }
        }


    }

    private fun uiVisibility(visibility: Boolean) {
        if (!visibility) {
            binding.containerUI.visibility = View.GONE
            binding.progressDialog.visibility = View.VISIBLE
        } else {
            binding.containerUI.visibility = View.VISIBLE
            binding.progressDialog.visibility = View.GONE
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(person: PersonDetails) {
        binding.apply {
            supportActionBar?.title = person.name
            if (isFavorite) tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_24)
            else tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            nameDetailsPerson.text = person.name
            if (person.gender == 1) gender.text = "Female" else gender.text = "Male"
            placeOfBirth.text = person.placeOfBirth
            birthday.text = person.birthday
            voteAverage.rating = person.popularity.toFloat()
            biographyText.text = person.biography

        }
        val poster = Cons.POSTER_BASE_URL + person.profilePath
        Glide.with(this)
            .load(poster)
            .placeholder(R.drawable.ic_placeholder_no_text)
            .into(binding.posterDetailsPerson)
    }

    override fun showDetailsMovie(id: Int) {
        val intent = Intent(this, DetailsMovieActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY, id)
        startActivity(intent)
    }

}