package com.example.mymovieapp.screen_person.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymovieapp.R
import com.example.mymovieapp.app.base.BaseBindingFragment
import com.example.mymovieapp.app.utils.Cons.Companion.PERSON_ID_KEY
import com.example.mymovieapp.databinding.FragmentPersonBinding
import com.example.mymovieapp.screen_movie.presentation.adapter.MovieLoaderStateAdapter
import com.example.mymovieapp.screen_person.domain.models.PersonResType
import com.example.mymovieapp.screen_person.presentation.adapter.PersonAdapter
import com.example.mymovieapp.screen_person.presentation.adapter.PersonItemOnClickListener
import com.example.mymovieapp.screen_person_details.presentation.DetailsPersonActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonFragment : BaseBindingFragment<FragmentPersonBinding>(FragmentPersonBinding::inflate),
    SwipeRefreshLayout.OnRefreshListener, PersonItemOnClickListener,
    SearchView.OnQueryTextListener {

    private val viewModel: PersonViewModel by viewModels()

    private val adapter: PersonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PersonAdapter(actionListener = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.responseType(PersonResType.PERSON)

        requireBinding().swiperefresh.setOnRefreshListener(this)
        requireBinding().swiperefresh.setColorSchemeResources(
            R.color.red,
            R.color.purple_200,
            R.color.green,
            R.color.blue)

        lifecycleScope.launch(Dispatchers.Main) { viewModel.personFlow.collectLatest(adapter::submitData) }

        requireBinding().personRecaycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoaderStateAdapter(),
            footer = MovieLoaderStateAdapter())

        adapter.addLoadStateListener { state ->
            with(requireBinding()) {
                personRecaycleView.isVisible = state.refresh != LoadState.Loading
                progressDialog.isVisible = state.refresh == LoadState.Loading
            }
            if (state.refresh is LoadState.Error)
                Toast.makeText(requireContext(),
                    (state.refresh as LoadState.Error).error.message ?: "",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        viewModel.responseType(PersonResType.PERSON)
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

    override fun showDetailsPerson(id: Int) {
        val intent = Intent(requireContext(), DetailsPersonActivity::class.java)
        intent.putExtra(PERSON_ID_KEY, id)
        requireContext().startActivity(intent)
    }

    override fun onQueryTextSubmit(searchText: String?): Boolean {
        if (searchText != null) viewModel.responseSearchType(searchText)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val searchText = newText!!.lowercase(Locale.getDefault())
        if (searchText.isNotEmpty()) viewModel.responseSearchType(searchText)
        else viewModel.responseType(PersonResType.PERSON)
        return false
    }

}


