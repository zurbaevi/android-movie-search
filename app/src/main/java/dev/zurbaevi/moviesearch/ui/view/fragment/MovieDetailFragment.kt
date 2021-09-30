package dev.zurbaevi.moviesearch.ui.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.databinding.FragmentMovieDetailBinding
import dev.zurbaevi.moviesearch.ui.viewmodel.MovieDetailViewModel
import dev.zurbaevi.moviesearch.utils.Resource

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private val binding: FragmentMovieDetailBinding by viewBinding()

    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        movieDetailViewModel.searchById(MovieDetailFragmentArgs.fromBundle(requireArguments()).imdbID)
    }

    private fun setupObserver() {
        binding.apply {
            movieDetailViewModel.movieDetailData.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        textViewTitleDetail.text = it.data?.title
                        textViewGenreDetail.text = it.data?.genre
                        textViewStar.text = it.data?.rating
                        textViewTimer.text = it.data?.time
                        textViewDate.text = it.data?.released
                        textViewOverview.text = it.data?.overview
                        textViewType.text = it.data?.type
                        textViewBoxOffice.text = it.data?.boxOffice
                        textViewLanguage.text = it.data?.language
                        textViewAwards.text = it.data?.awards
                        textViewProduction.text = it.data?.production
                        Glide.with(imageViewPoster.context)
                            .load(it.data?.poster)
                            .error(R.drawable.ic_image_error)
                            .into(imageViewPoster)
                        progressBar.visibility = View.GONE
                    }
                    Resource.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    Resource.Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        DynamicToast.makeError(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }
}