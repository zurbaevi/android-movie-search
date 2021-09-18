package dev.zurbaevi.moviesearch.ui.main.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import dev.zurbaevi.moviesearch.R
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
        setupUI()
        setupObserver()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
        val search = menu.findItem(R.id.appbarSearch)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchViewModel.searchByName(query.toString())
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
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(it.data?.movieEntityList)
                    }
                    Status.LOADING -> {
                        binding.recyclerView.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        DynamicToast.makeError(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    Status.NOT_FOUND -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        DynamicToast.makeWarning(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    override fun onItemClick(taskEntry: MovieModel) {
        Toast.makeText(requireContext(), "Movie clicked!", Toast.LENGTH_SHORT).show()
    }

}