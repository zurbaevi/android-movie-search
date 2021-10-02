package dev.zurbaevi.moviesearch.data.repisitory

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.Movie
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviePagingSource(
    private val apiService: ApiService,
    private val query: String?,
) : RxPagingSource<Int, Movie>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val page = params.key ?: 1
        return if (query != null) {
            apiService.searchMovies(query, page)
                .subscribeOn(Schedulers.io())
                .map {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.results.isEmpty()) null else page + 1
                    ) as LoadResult<Int, Movie>
                }
                .onErrorReturn {
                    LoadResult.Error(it)
                }
        } else {
            apiService.getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io())
                .map {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.results.isEmpty()) null else page + 1
                    ) as LoadResult<Int, Movie>
                }
                .onErrorReturn {
                    LoadResult.Error(it)
                }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}