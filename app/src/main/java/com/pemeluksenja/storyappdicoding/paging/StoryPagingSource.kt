package com.pemeluksenja.storyappdicoding.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pemeluksenja.storyappdicoding.model.ListStoryItem
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIServices

class StoryPagingSource(private val apiService: APIServices, private val token: String) :
    PagingSource<Int, ListStoryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            Log.d("TokenStoryPaging: ", token)
            val client =
                apiService.getStoriesWithPageAndSize("Bearer $token", position, params.loadSize)
            Log.d("PTSGBG: ", client.listStory.toString())
            LoadResult.Page(
                data = client.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (client.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}