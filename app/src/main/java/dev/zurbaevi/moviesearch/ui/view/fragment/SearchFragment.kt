package dev.zurbaevi.moviesearch.ui.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dagger.hilt.android.AndroidEntryPoint
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.databinding.FragmentSearchBinding
import dev.zurbaevi.moviesearch.ui.adapter.MovieAdapter
import dev.zurbaevi.moviesearch.ui.viewmodel.SearchViewModel
import dev.zurbaevi.moviesearch.utils.Resource

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MovieAdapter.OnItemClickListener {

    private val binding: FragmentSearchBinding by viewBinding()

    private val searchViewModel by viewModels<SearchViewModel>()

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.menuSearch)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchViewModel.searchByName(query.toString())
                    searchView.onActionViewCollapsed()
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupUI() {
        binding.apply {
            recyclerView.adapter = adapter
        }
    }

    private fun setupObserver() {
        binding.apply {
            searchViewModel.movieListData.observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        adapter.submitList(it.data?.movieEntityList)
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        screenInfoHide()
                    }
                    Resource.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        screenInfoHide()
                    }
                    Resource.Status.ERROR -> {
                        screenInfoShow()
                        recyclerView.visibility = View.GONE
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

    override fun onItemClick(imdbID: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(
                imdbID
            )
        )
    }

    private fun screenInfoHide() {
        binding.textViewInfo.visibility = View.GONE
        binding.imageViewInfo.visibility = View.GONE
    }

    private fun screenInfoShow() {
        binding.textViewInfo.visibility = View.VISIBLE
        binding.imageViewInfo.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}