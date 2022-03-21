package com.example.mymovieapp.screen_movie.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymovieapp.R
import com.example.mymovieapp.app.base.BaseBindingFragment
import com.example.mymovieapp.app.utils.Cons.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.databinding.FragmentMovieBinding
import com.example.mymovieapp.screen_movie.domain.model.ResponseUser
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieAdapter
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieItemOnClickListener
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieLoaderStateAdapter
import com.example.mymovieapp.screen_movie_details.presentation.ui.DetailsMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalCoroutinesApi
@kotlinx.coroutines.DelicateCoroutinesApi
@AndroidEntryPoint
class MovieFragment : BaseBindingFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate),
    AdapterView.OnItemSelectedListener,
    SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, MovieItemOnClickListener {

    private val viewModel: MovieViewModel by viewModels()

    private val adapter: MovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieAdapter(actionListener = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val arrayAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.categories,
            (R.layout.item_spinner_custom))


        requireBinding().apply {
            swiperefresh.setOnRefreshListener(this@MovieFragment)
            swiperefresh.setColorSchemeResources(
                R.color.red,
                R.color.red,
                R.color.red,
                R.color.red)

            spinnerCategories.adapter = arrayAdapter
            spinnerCategories.onItemSelectedListener = this@MovieFragment
        }

        lifecycleScope.launch(Dispatchers.Main) { viewModel.movieFlow.collectLatest(adapter::submitData) }


        requireBinding().recaycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoaderStateAdapter(),
            footer = MovieLoaderStateAdapter())

        adapter.addLoadStateListener { state ->
            with(requireBinding()) {
                recaycleView.isVisible = state.refresh != LoadState.Loading
                progressDialog.isVisible = state.refresh == LoadState.Loading
            }
            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemSelected(
        p0: AdapterView<*>?,
        p1: View?,
        position: Int,
        p3: Long,
    ) {
        when (position) {
            0 -> viewModel.responseType(ResponseUser.POPULAR)

            1 -> viewModel.responseType(ResponseUser.NOW_PLAYING)

            2 -> viewModel.responseType(ResponseUser.TOP_RATED)

            else -> viewModel.responseType(ResponseUser.UPCOMING)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) = Unit

    override fun onRefresh() {
        viewModel.responseType(viewModel.lastResponse.value!!)
        requireBinding().swiperefresh.isRefreshing = true
        requireBinding().swiperefresh.postDelayed({
            requireBinding().swiperefresh.isRefreshing = false
        }, 1500)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) {
            requireBinding().spinnerCategories.visibility = View.GONE
            viewModel.responseSearchType(searchText)
        }
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText!!.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) {
            requireBinding().spinnerCategories.visibility = View.GONE
            viewModel.responseSearchType(searchText)
        } else {
            requireBinding().spinnerCategories.visibility = View.VISIBLE
            viewModel.responseType(viewModel.lastResponse.value!!)
        }
        return false
    }

    override fun showDetailsMovie(id: Int) {
        val intent = Intent(requireContext(), DetailsMovieActivity::class.java)
        intent.putExtra(MOVIE_ID_KEY, id)
        requireContext().startActivity(intent)

    }


}