package com.pemeluksenja.storyappdicoding.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pemeluksenja.storyappdicoding.R
import com.pemeluksenja.storyappdicoding.model.ListStoryItem
import com.pemeluksenja.storyappdicoding.model.StoryResponse
import com.pemeluksenja.storyappdicoding.model.TimelineStory
import com.pemeluksenja.storyappdicoding.retrofitconfig.APIConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    val storyList = MutableLiveData<ArrayList<TimelineStory>>()
    val context = application
    fun getStories() {
        val list = ArrayList<TimelineStory>()
        val pref =
            context.getSharedPreferences(R.string.token_pref.toString(), Context.MODE_PRIVATE)
        val token = pref.getString(R.string.token.toString(), "")!!
        Log.d("TokenViewModel: ", token)
        val client = APIConfig.getAPIServices().getStoriesWithLocation("Bearer $token", 1, 15, 1)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    if (resBody !== null) {
                        val items = resBody.listStory
                        for (user in items) {
                            Log.d("APIREQUEST", user.toString())
                            list.add(setStories(user))
                        }
                        storyList.postValue(list)
                    }
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setStories(story: ListStoryItem): TimelineStory {
        val name = "@${story.name}"!!
        val image = story.photoUrl!!
        val desc = story.description!!
        val id = story.id
        val lon = story.lon
        val lat = story.lat
        return TimelineStory(name, image, desc, id, lon as Double?, lat as Double?)
    }

    fun getStoryTimeline(): LiveData<ArrayList<TimelineStory>> {
        return storyList
    }

    companion object {
        private val TAG = "MainViewModel"
    }
}