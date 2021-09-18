package dev.zurbaevi.moviesearch.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dev.zurbaevi.moviesearch.data.api.RetrofitBuilder
import dev.zurbaevi.moviesearch.data.model.MovieModel
import dev.zurbaevi.moviesearch.databinding.FragmentSearchBinding
import dev.zurbaevi.moviesearch.ui.base.SearchViewModelFactory
import dev.zurbaevi.moviesearch.ui.main.adapter.MovieAdapter
import dev.zurbaevi.moviesearch.ui.main.viewmodel.SearchViewModel
import dev.zurbaevi.moviesearch.utils.Status


class SearchFragment : Fragment(), MovieAdapter.OnItemClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory(RetrofitBuilder.apiService)
    }

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonSearch.setOnClickListener {
            searchViewModel.searchByName(binding.editTextSearch.text.toString())
        }

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.apply {
            recyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {
        binding.apply {
            searchViewModel.movieListData.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.SUCCESS -> adapter.submitList(it.data?.movieEntityList)
                }
            })
        }
    }

    private fun renderList(users: List<MovieModel>) {
        adapter.submitList(users)
    }

    override fun onItemClick(taskEntry: MovieModel) {
        Toast.makeText(requireContext(), "Movie clicked!", Toast.LENGTH_SHORT).show()
    }

}