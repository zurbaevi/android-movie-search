package dev.zurbaevi.moviesearch.ui.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.databinding.FragmentMovieDetailsBinding

@AndroidEntryPoint
class TelevisionDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<TelevisionDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieDetailsBinding.bind(view)

        binding.apply {
            val television = args.television
            Glide.with(this@TelevisionDetailsFragment)
                .load("${ApiService.IMAGE_BASE_URL}${television.posterPath}")
                .error(R.drawable.ic_image_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewMovieDescription.isVisible = true
                        textViewMovieTitle.isVisible = true
                        return false
                    }
                })
                .into(imageViewMoviePoster)

            textViewMovieDescription.text = television.overview
            textViewMovieTitle.text = television.name
        }

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}