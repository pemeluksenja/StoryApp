package com.pemeluksenja.storyappdicoding.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.pemeluksenja.storyappdicoding.utils.Converter
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
@Parcelize
@Entity (tableName = "story")
data class StoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
): Parcelable
@Parcelize
@Entity (tableName = "story")
@TypeConverters(Converter::class)
data class ListStoryItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: @RawValue Any? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: @RawValue Any? = null
): Parcelable
