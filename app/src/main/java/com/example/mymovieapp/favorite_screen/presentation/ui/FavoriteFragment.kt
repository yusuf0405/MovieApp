package com.example.mymovieapp.favorite_screen.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FavoriteMovieFragmentBinding
import com.example.mymovieapp.favorite_screen.domain.models.FavoriteMovie
import com.example.mymovieapp.favorite_screen.domain.models.FavoritePerson
import com.example.mymovieapp.favorite_screen.presentation.adapter.FavMovieItemOnClick
import com.example.mymovieapp.favorite_screen.presentation.adapter.FavPersonItemOnClick
import com.example.mymovieapp.favorite_screen.presentation.adapter.FavoriteMovieAdapter
import com.example.mymovieapp.favorite_screen.presentation.adapter.FavoritePersonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val binding: FavoriteMovieFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteMovieFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: FavoriteViewModel by viewModels()

    private val movieAdapter = FavoriteMovieAdapter(object : FavMovieItemOnClick {
        override fun deleteMovie(movie: FavoriteMovie) {
            viewModel.deleteFavoriteMovies(movie = movie)
        }

    })
    private val personAdapter = FavoritePersonAdapter(object : FavPersonItemOnClick {
        override fun deletePerson(person: FavoritePerson) {
            viewModel.deleteFavoritePerson(person = person)
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val arrayAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.favorite,
            android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_custom_item)
        movieAdapter.favoriteMovieList
        binding.favSpinner.adapter = arrayAdapter
        binding.favSpinner.onItemSelectedListener = this


        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            movieAdapter.favoriteMovieList = it
        }

        viewModel.favoritePersons.observe(viewLifecycleOwner) {
            personAdapter.favPersonList = it
        }

        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (position) {
            0 -> {
                viewModel.allFavoriteMovies()
                binding.favMovieRv.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.favMovieRv.adapter = movieAdapter

            }
            else -> {
                viewModel.allFavoritePersons()
                binding.favMovieRv.layoutManager = LinearLayoutManager(requireContext())
                binding.favMovieRv.adapter = personAdapter
            }
        }
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
    }


}