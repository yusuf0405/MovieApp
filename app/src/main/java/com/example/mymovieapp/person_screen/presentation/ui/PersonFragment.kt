package com.example.mymovieapp.person_screen.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mymovieapp.R
import com.example.mymovieapp.app.utils.Utils.Companion.PERSON_ID_KEY
import com.example.mymovieapp.databinding.PersonFragmentBinding
import com.example.mymovieapp.movie_screen.presentation.adapter.MovieLoaderStateAdapter
import com.example.mymovieapp.person_details_screen.presentation.ui.DetailsPersonActivity
import com.example.mymovieapp.person_screen.domain.models.PersonResType
import com.example.mymovieapp.person_screen.presentation.adapter.PersonAdapter
import com.example.mymovieapp.person_screen.presentation.adapter.PersonItemOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, PersonItemOnClickListener,
    SearchView.OnQueryTextListener {

    private val binding: PersonFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        PersonFragmentBinding.inflate(layoutInflater)
    }
    private val viewModel: PersonViewModel by viewModels()

    private val adapter: PersonAdapter by lazy(LazyThreadSafetyMode.NONE) { PersonAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.responseType(PersonResType.PERSON)

        binding.swiperefresh.setOnRefreshListener(this)
        binding.swiperefresh.setColorSchemeResources(
            R.color.red,
            R.color.red,
            R.color.red,
            R.color.red)

        GlobalScope.launch(Dispatchers.Main) { viewModel.personFlow.collectLatest(adapter::submitData) }

        binding.personRecaycleView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoaderStateAdapter(),
            footer = MovieLoaderStateAdapter())

        adapter.addLoadStateListener { state ->
            with(binding) {
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
        binding.swiperefresh.isRefreshing = true
        binding.swiperefresh.postDelayed({ binding.swiperefresh.isRefreshing = false }, 500)
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


