package com.pemeluksenja.storyappdicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pemeluksenja.storyappdicoding.databinding.ActivityStoryDetailBinding
import com.pemeluksenja.storyappdicoding.model.ListStoryItem

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityStoryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val story = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY) as ListStoryItem
        bind.userNameDetail.text = story.name
        Glide.with(this).load(story.photoUrl).into(bind.userImageStoryDetail)
        bind.storyDesc.text = story.description
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}