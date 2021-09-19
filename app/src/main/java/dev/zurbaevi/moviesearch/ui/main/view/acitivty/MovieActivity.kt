package dev.zurbaevi.moviesearch.ui.main.view.acitivty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.zurbaevi.moviesearch.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}