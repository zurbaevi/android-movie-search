package dev.zurbaevi.moviesearch.ui.main.view.acitivty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.favoriteFragment,
                R.id.searchFragment,
                R.id.movieDetailFragment
            )
        )
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)

    }
}