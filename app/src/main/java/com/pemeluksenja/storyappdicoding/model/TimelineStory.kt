package com.pemeluksenja.storyappdicoding.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "story")
data class TimelineStory(
    val name: String,
    val image: String,
    val desc: String,
    @PrimaryKey
    val id: String,
    val lon: Double? = null,
    val lat: Double? = null,
) : Parcelable
