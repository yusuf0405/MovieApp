package com.example.mymovieapp.movie_screen.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils.Companion.MOVIE_ID_KEY
import com.example.mymovieapp.databinding.MovieFragmentBinding
import com.example.mymovieapp.movie_details_screen.presentation.ui.DetailsMovieActivity
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.presentation.adapter.ItemOnClickListener
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieAdapter
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieLoaderStateAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalCoroutinesApi
@kotlinx.coroutines.DelicateCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(), AdapterView.OnItemSelectedListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: MovieViewModel by viewModels()

    private val binding: MovieFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        MovieFragmentBinding.inflate(layoutInflater)
    }
    private val adapter = MovieAdapter(
        actionListener = object : ItemOnClickListener {
            override fun showDetailsMovie(id: Int) {
                val intent = Intent(requireContext(), DetailsMovieActivity::class.java)
                intent.putExtra(MOVIE_ID_KEY, id)
                requireContext().startActivity(intent)

            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val arrayAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_custom_item)
        binding.swiperefresh.setOnRefreshListener(this)
        binding.swiperefresh.setColorSchemeResources(
            R.color.red,
            R.color.red,
            R.color.red,
            R.color.red)
        binding.spinnerCategories.adapter = arrayAdapter
        binding.spinnerCategories.onItemSelectedListener = this



        GlobalScope.launch(Dispatchers.Main) {
            viewModel.movieFlow.collectLatest(adapter::submitData)
        }

        binding.recaycleView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recaycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoaderStateAdapter(),
            footer = MovieLoaderStateAdapter()
        )

        adapter.addLoadStateListener { state ->
            with(binding) {
                recaycleView.isVisible = state.refresh != LoadState.Loading
                progressDialog.isVisible = state.refresh == LoadState.Loading
            }
            if (state.refresh is LoadState.Error) {
                Snackbar.make(binding.root,
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Snackbar.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    override fun onItemSelected(
        p0: AdapterView<*>?,
        p1: View?,
        position: Int,
        p3: Long,
    ) {
        when (position) {
            0 -> {
                viewModel.responseType(ResponseUser.POPULAR)
            }
            1 -> {
                viewModel.responseType(ResponseUser.NOW_PLAYING)
            }
            2 -> {
                viewModel.responseType(ResponseUser.TOP_RATED)
            }
            else -> {
                viewModel.responseType(ResponseUser.UPCOMING)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onRefresh() {
        viewModel.responseType(viewModel.oldResponse)
        binding.swiperefresh.isRefreshing = true
        binding.swiperefresh.postDelayed({
            binding.swiperefresh.isRefreshing = false
        }, 500)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.search_action)
        var oldResponse: ResponseUser? = null
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                if (searchText != null) {
                    oldResponse = viewModel.oldResponse
                    binding.spinnerCategories.visibility = View.GONE
                    viewModel.responseSearchType(searchText)
                }
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    oldResponse = viewModel.oldResponse
                    binding.spinnerCategories.visibility = View.GONE
                    viewModel.responseSearchType(searchText)
                } else {
                    binding.spinnerCategories.visibility = View.VISIBLE
                    if (oldResponse != null) viewModel.responseType(oldResponse!!)
                }
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}