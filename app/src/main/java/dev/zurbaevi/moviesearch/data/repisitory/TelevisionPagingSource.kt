package dev.zurbaevi.moviesearch.data.repisitory

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import dev.zurbaevi.moviesearch.data.api.ApiService
import dev.zurbaevi.moviesearch.data.model.Movie
import dev.zurbaevi.moviesearch.data.model.Television
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TelevisionPagingSource(
    private val apiService: ApiService,
    private val query: String?,
) : RxPagingSource<Int, Television>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Television>> {
        val page = params.key ?: 1
        return if (query != null) {
            apiService.searchTelevision(query, page)
                .subscribeOn(Schedulers.io())
                .map {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.results.isEmpty()) null else page + 1
                    ) as LoadResult<Int, Television>
                }
                .onErrorReturn {
                    LoadResult.Error(it)
                }
        } else {
            apiService.getTelevisionPopular(page)
                .subscribeOn(Schedulers.io())
                .map {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (it.results.isEmpty()) null else page + 1
                    ) as LoadResult<Int, Television>
                }
                .onErrorReturn {
                    LoadResult.Error(it)
                }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Television>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}