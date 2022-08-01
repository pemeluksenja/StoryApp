package com.pemeluksenja.storyappdicoding.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.*
import com.pemeluksenja.storyappdicoding.model.ListStoryItem
import com.pemeluksenja.storyappdicoding.paging.StoryRepository
import com.pemeluksenja.storyappdicoding.viewmodelfactory.Injection

class TimelineStoryViewModel(storyRepository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)

    companion object {
        private val TAG = "MainViewModel"
    }
}

class ViewModelFactory(private val context: Context, private val token: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimelineStoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimelineStoryViewModel(Injection.provideRepository(context, token)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}