package com.example.mymovieapp.person_details_screen.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.PERSON_ID_KEY
import com.example.mymovieapp.databinding.ActivityDetailsPersonBinding
import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson
import com.example.mymovieapp.movie_details_screen.presentation.adapter.MovieAdapter
import com.example.mymovieapp.movie_details_screen.presentation.ui.DetailsMovieActivity
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieItemOnClickListener
import com.example.mymovieapp.person_details_screen.domain.models.PersonDetails
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

    private val adapter: MovieAdapter by lazy(LazyThreadSafetyMode.NONE) { MovieAdapter(this) }

    private var favoritePersons: List<FavoritePerson>? = null
    private var person: PersonDetails? = null
    private var isFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter

        val id = intent?.extras?.getInt(PERSON_ID_KEY)
        viewModel.getPersonDetails(id = id!!)
        viewModel.getPersonCreditMovies(id = id)

        viewModel.allFavoritePersons()
        uiVisibility(false)

        viewModel.movieFavList.observe(this) { favoritePersons = it }
        viewModel.movieListResponse.observe(this) { adapter.movieList = it.body()!!.crew }

        viewModel.personOfList.observe(this) { response ->
            if (response.isSuccessful) {
                person = response.body()!!
                uiVisibility(true)
                supportActionBar?.title = response.body()!!.name
                bindUI(person!!, favoritePersons!!)
            } else {
                uiVisibility(true)
                Toast.makeText(this, response.message(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvFavoritePerson.setOnClickListener {
            if (isFavorite) {
                isFavorite = false
                binding.tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                viewModel.deletePersonFavorite(person = person!!)
            } else {
                isFavorite = true
                viewModel.addPersonFavorite(person = person!!)
                binding.tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }


    }

    private fun uiVisibility(containerUI: Boolean) {
        if (!containerUI) {
            binding.containerUI.visibility = View.GONE
            binding.progressDialog.visibility = View.VISIBLE
        } else {
            binding.containerUI.visibility = View.VISIBLE
            binding.progressDialog.visibility = View.GONE
        }

    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(person: PersonDetails, favoritePersons: List<FavoritePerson>) {
        favoritePersons.forEach {
            if (person.id == it.id) isFavorite = true
        }
        binding.apply {
            if (isFavorite) tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_24)
            else tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            nameDetailsPerson.text = person.name
            if (person.gender == 1) gender.text = "Female" else gender.text = "Male"
            placeOfBirth.text = person.placeOfBirth
            birthday.text = person.birthday
            voteAverage.rating = person.popularity.toFloat()
            biographyText.text = person.biography

        }
        val poster = Utils.POSTER_BASE_URL + person.profilePath
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