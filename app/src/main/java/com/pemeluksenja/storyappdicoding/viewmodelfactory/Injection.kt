package com.pemeluksenja.storyappdicoding.viewmodelfactory

import android.content.Context
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIConfig
import com.pemeluksenja.storyappdicoding.paging.StoryDatabase
import com.pemeluksenja.storyappdicoding.paging.StoryRepository

object Injection {
    fun provideRepository(context: Context, token: String): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = APIConfig.getAPIServices()
        return StoryRepository(database, apiService, token)
    }
}