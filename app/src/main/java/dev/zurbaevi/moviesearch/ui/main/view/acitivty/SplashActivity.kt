package dev.zurbaevi.moviesearch.ui.main.view.acitivty

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.zurbaevi.moviesearch.databinding.ActivitySplashBinding
import dev.zurbaevi.moviesearch.ui.main.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.initSplashScreen()
        implementsObservers()
    }

    private fun implementsObservers() {
        splashViewModel.liveData.observe(this, {
            startActivity(Intent(this, MovieActivity::class.java))
        })
    }

}