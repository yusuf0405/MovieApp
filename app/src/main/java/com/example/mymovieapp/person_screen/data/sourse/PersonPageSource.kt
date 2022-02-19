package com.example.mymovieapp.person_screen.data.sourse

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymovieapp.person_screen.domain.model.Person
import com.example.mymovieapp.app.utils.Utils.Companion.MAX_PAGE_SIZE
import com.example.mymovieapp.person_screen.domain.model.ResponsePersonType
import com.example.mymovieapp.person_screen.domain.repository.PersonRepository
import retrofit2.HttpException

class PersonPageSource(
    private val api: PersonRepository,
    private val responseType: ResponsePersonType,
    private val query: String,
) : PagingSource<Int, Person>() {
    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtLeast(MAX_PAGE_SIZE)
        return try {
            val response = when (responseType) {
                ResponsePersonType.PERSON -> {
                    api.getPopularPerson(page = page, pageSize = pageSize)
                }
                else -> {
                    api.getSearchPerson(query = query, page = page, pageSize = pageSize)
                }
            }
            if (response.isSuccessful) {
                val person = checkNotNull(response.body()?.personList)
                val nextKey = if (person.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(data = person, prevKey = prevKey, nextKey = nextKey)
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
