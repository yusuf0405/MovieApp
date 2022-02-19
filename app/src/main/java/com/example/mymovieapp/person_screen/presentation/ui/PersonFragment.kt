package com.example.mymovieapp.person_screen.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymovieapp.R
import com.example.mymovieapp.app.adapter.PersonAdapter
import com.example.mymovieapp.app.adapter.PersonItemOnClickListener
import com.example.mymovieapp.app.utils.Utils.Companion.PERSON_ID_KEY
import com.example.mymovieapp.databinding.PersonFragmentBinding
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieLoaderStateAdapter
import com.example.mymovieapp.person_screen.domain.model.ResponsePersonType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val binding: PersonFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        PersonFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: PersonViewModel by viewModels()

    private val adapter = PersonAdapter(object : PersonItemOnClickListener {

        override fun showDetailsPerson(id: Int) {
            findNavController().navigate(R.id.detailsPersonFragment,
                bundleOf(PERSON_ID_KEY to id))
        }

    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.responseType(ResponsePersonType.PERSON)
        binding.swiperefresh.setOnRefreshListener(this)
        binding.swiperefresh.setColorSchemeResources(
            R.color.red,
            R.color.red,
            R.color.red,
            R.color.red)

        GlobalScope.launch(Dispatchers.Main) {
            viewModel.personFlow.collectLatest(adapter::submitData)
        }

        binding.personRecaycleView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.personRecaycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoaderStateAdapter(),
            footer = MovieLoaderStateAdapter()
        )

        adapter.addLoadStateListener { state ->
            with(binding) {
                personRecaycleView.isVisible = state.refresh != LoadState.Loading
                progressDialog.isVisible = state.refresh == LoadState.Loading
            }
            if (state.refresh is LoadState.Error) {
                Snackbar.make(binding.root,
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Snackbar.LENGTH_LONG).show()
            }
        }

        binding.serchPerson.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchText: String?): Boolean {
                if (searchText != null) {
                    viewModel.responseSearchType(searchText)
                }
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    viewModel.responseSearchType(searchText)
                } else {
                    viewModel.responseType(ResponsePersonType.PERSON)
                }
                return false
            }

        })
        return binding.root
    }

    override fun onRefresh() {
        viewModel.responseType(ResponsePersonType.PERSON)
        binding.swiperefresh.isRefreshing = true
        binding.swiperefresh.postDelayed({
            binding.swiperefresh.isRefreshing = false
        }, 500)
    }
}


