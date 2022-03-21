package com.example.mymovieapp.screen_favorite.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.R
import com.example.mymovieapp.app.base.BaseBindingFragment
import com.example.mymovieapp.databinding.FavoriteFragmentBinding
import com.example.mymovieapp.screen_favorite.domain.models.FavoriteMovie
import com.example.mymovieapp.screen_favorite.domain.models.FavoritePerson
import com.example.mymovieapp.screen_favorite.presentation.adapter.FavItemOnClick
import com.example.mymovieapp.screen_favorite.presentation.adapter.FavoriteMovieAdapter
import com.example.mymovieapp.screen_favorite.presentation.adapter.FavoritePersonAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment :
    BaseBindingFragment<FavoriteFragmentBinding>(FavoriteFragmentBinding::inflate),
    AdapterView.OnItemSelectedListener, FavItemOnClick {

    private val viewModel: FavoriteViewModel by viewModels()

    private val movieAdapter: FavoriteMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoriteMovieAdapter(actionListener = this)
    }
    private val personAdapter: FavoritePersonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FavoritePersonAdapter(actionListener = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.favorite,
            R.layout.item_spinner_custom)
        movieAdapter.favoriteMovieList
        requireBinding().favSpinner.adapter = arrayAdapter
        requireBinding().favSpinner.onItemSelectedListener = this

        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movieAdapter.favoriteMovieList = it }

        viewModel.favoritePersons.observe(viewLifecycleOwner) { personAdapter.favPersonList = it }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (position) {
            0 -> {
                viewModel.allFavoriteMovies()
                requireBinding().favMovieRv.layoutManager = GridLayoutManager(requireContext(), 2)
                requireBinding().favMovieRv.adapter = movieAdapter

            }
            else -> {
                viewModel.allFavoritePersons()
                requireBinding().favMovieRv.layoutManager = LinearLayoutManager(requireContext())
                requireBinding().favMovieRv.adapter = personAdapter
            }
        }
    }


    override fun onNothingSelected(p0: AdapterView<*>?) = Unit

    override fun deletePerson(person: FavoritePerson) = viewModel.deleteFavoritePerson(person)

    override fun deleteMovie(movie: FavoriteMovie) = viewModel.deleteFavoriteMovies(movie = movie)

}


