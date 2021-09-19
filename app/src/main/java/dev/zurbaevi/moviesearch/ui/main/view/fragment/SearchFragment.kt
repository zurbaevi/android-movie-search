package dev.zurbaevi.moviesearch.ui.main.view.fragment

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.data.api.RetrofitBuilder
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
                    Status.SUCCESS -> {
                        adapter.submitList(it.data?.movieEntityList)
                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        screenInfoHide()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        screenInfoHide()
                    }
                    Status.ERROR -> {
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