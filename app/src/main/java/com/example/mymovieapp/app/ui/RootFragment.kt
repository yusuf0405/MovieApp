package com.example.mymovieapp.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.FragmentRootBinding
import com.example.mymovieapp.movie_screen.presentation.ui.MovieFragment
import com.example.mymovieapp.person_screen.presentation.ui.PersonFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class RootFragment : Fragment() {

    //Создаем ViewBinding
    private val binding: FragmentRootBinding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentRootBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding.tableLayout.tabIconTint = null

        val fragmentList = arrayListOf(
            MovieFragment(), PersonFragment()
        )
        val adapter = ViewPagerAdapter(
            list = fragmentList,
            fm = requireActivity().supportFragmentManager,
            lifecycle = lifecycle)
        binding.viewPager2.adapter = adapter
        binding.viewPager2.isSaveEnabled = false
        Log.i("POSITION", binding.viewPager2.currentItem.toString())


        TabLayoutMediator(binding.tableLayout, binding.viewPager2) { tab, pos ->
            when (pos) {
                0 -> {
                    tab.setIcon(R.drawable.ic_baseline_movie_filter_24)

                }
                1 -> {
                    tab.setIcon(R.drawable.ic_baseline_person_24)
                }
            }
        }.attach()
        return binding.root
    }

}