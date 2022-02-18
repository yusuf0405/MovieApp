package com.example.mymovieapp.movie_screen.data.souse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovieapp.app.utils.Utils.Companion.MAX_PAGE_SIZE
import com.example.mymovieapp.movie_screen.domain.model.Movie
import com.example.mymovieapp.movie_screen.domain.model.ResponseUser
import com.example.mymovieapp.movie_screen.domain.repository.MovieRepository
import retrofit2.HttpException


class MoviePageSource(
    private val api: MovieRepository,
    private val responseType: ResponseUser,
    private val query: String,


    ) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
        return try {
            val response = when (responseType) {
                ResponseUser.TOP_RATED -> {
                    api.getTopRatedMovie(page = page, pageSize = pageSize)
                }
                ResponseUser.NOW_PLAYING -> {
                    api.getNowPlayingMovie(page = page, pageSize = pageSize)
                }
                ResponseUser.POPULAR -> {
                    api.getPopularMovie(page = page, pageSize = pageSize)
                }
                ResponseUser.UPCOMING -> {
                    api.getUpcomingMovie(page = page, pageSize = pageSize)
                }
                else -> {
                    api.getSearchMovie(query = query, page = page, pageSize = pageSize)
                }
            }
            if (response.isSuccessful) {
                val movie = checkNotNull(response.body()?.movieList)
                val nextKey = if (movie.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(movie, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}