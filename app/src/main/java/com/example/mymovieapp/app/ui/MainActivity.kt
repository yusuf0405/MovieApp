package com.example.mymovieapp.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymovieapp.R
import com.example.mymovieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        setBottomNavigationWithNavController()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_movie, R.id.navigation_person, R.id.navigation_favorite))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


//    private fun setBottomNavigationWithNavController() {
//
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//
//        val menuItems = arrayOf(
//            Model(
//                icon = R.drawable.ic_baseline_movie_filter_24,                // Icon
//                destinationId = R.id.movieFragment,     // destinationID
//                id = 0,                // ID
//                text = R.string.title_movie,               // Icon with Text, If you don't want text then don't pass it
//                count = R.string.empty_value              // notification count if you want to show then pass specific count else pass R.string.empty_value or don't pass anything
//            ),
//            Model(
//                icon = R.drawable.ic_baseline_person_24,
//                destinationId = R.id.personFragment,
//                id = 1,
//                text = R.string.title_person
//            ),
//            Model(
//                R.drawable.ic_baseline_favorite_24,
//                R.id.favoriteMovieFragment,
//                2,
//                R.string.title_favorite,
//                R.string.empty_value
//            ),
//        )
//
//        binding.bottomNavigation.apply {
//            setMenuItems(menuItems, 0)
//            setupWithNavController(navController)
//        }
//
//    }
}