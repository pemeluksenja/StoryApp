package com.pemeluksenja.storyappdicoding.paging

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.pemeluksenja.storyappdicoding.model.ListStoryItem
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIServices

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: APIServices,
    private val token: String
) {
    fun getStory(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token)
            }
        ).liveData
    }

}