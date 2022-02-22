package com.example.mymovieapp.person_details_screen.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.movie_screen.presentation.adapter.ItemOnClickListener
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.PERSON_ID_KEY
import com.example.mymovieapp.app.utils.Utils.Companion.POSTER_BASE_URL
import com.example.mymovieapp.databinding.DetailsPersonFragmentBinding
import com.example.mymovieapp.favorite_screen.domain.model.FavoritePerson
import com.example.mymovieapp.movie_details_screen.presentation.adapter.MovieAdapter
import com.example.mymovieapp.movie_details_screen.presentation.ui.DetailsMovieActivity
import com.example.mymovieapp.person_details_screen.domain.model.PersonDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class DetailsPersonFragment : Fragment() {
    private val binding: DetailsPersonFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        DetailsPersonFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailsPersonViewModel by viewModels()
    private var favoritePersons: List<FavoritePerson>? = null
    private var person: PersonDetails? = null
    private var isFavorite = false

    private val adapter = MovieAdapter(object : ItemOnClickListener {
        override fun showDetailsMovie(id: Int) {
            val intent = Intent(requireContext(), DetailsMovieActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, id)
            requireContext().startActivity(intent)
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        val bundle = arguments
        if (bundle != null) {
            GlobalScope.launch(Dispatchers.Main) {
                uiVisibility(false)
                favoritePersons = viewModel.allFavoritePersons()
                delay(2000)
                val id = arguments?.getInt(PERSON_ID_KEY) as Int
                viewModel.getPersonDetails(id = id)
                viewModel.getPersonCreditMovies(id = id)
            }

        }

        viewModel.movieListResponse.observe(viewLifecycleOwner) { response ->
            adapter.movieList = response.body()!!.crew

        }
        viewModel.personOfList.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                person = response.body()!!
                uiVisibility(true)
                bindUI(person!!, favoritePersons!!)
            } else {
                uiVisibility(true)
                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.back.setOnClickListener {
//            findNavController().navigate(R.id.action_detailsPersonFragment_to_rootFragment)
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

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(person: PersonDetails, favoritePersons: List<FavoritePerson>) {
        favoritePersons.forEach {
            if (person.id == it.id) {
                isFavorite = true
            }
        }
        val poster = POSTER_BASE_URL + person.profilePath
        binding.apply {
            if (isFavorite) {
                tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                tvFavoritePerson.setImageResource(R.drawable.ic_baseline_favorite_border_24)

            }
            nameDetailsPerson.text = person.name
            if (person.gender == 1) gender.text = "Female"
            else gender.text = "Male"
            placeOfBirth.text = person.placeOfBirth
            birthday.text = person.birthday
            voteAverage.rating = person.popularity.toFloat()
            biographyText.text = person.biography
            Glide.with(requireActivity())
                .load(poster)
                .placeholder(R.drawable.ic_placeholder_no_text)
                .into(posterDetailsPerson)
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


}