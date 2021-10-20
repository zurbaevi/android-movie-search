package dev.zurbaevi.moviesearch.ui.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dev.zurbaevi.moviesearch.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val movie = args.movie
            Glide.with(this@DetailsFragment)
                .load("${movie.baseUrl}${movie.posterPath}")
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

            textViewMovieDescription.text = movie.overview
            textViewMovieTitle.text = movie.originalTitle
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}