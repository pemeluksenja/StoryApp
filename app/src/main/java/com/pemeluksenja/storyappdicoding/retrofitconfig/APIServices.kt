package com.pemeluksenja.storyappdicoding.retrofitconfig

import com.pemeluksenja.storyappdicoding.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIServices {
    @GET("stories")
    fun getStories(
        @Header("Authorization") Token: String,
    ): Call<StoryResponse>

    @GET("stories")
    suspend fun getStoriesWithPageAndSize(
        @Header("Authorization") Token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories")
    fun getStoriesWithLocation(
        @Header("Authorization") Token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int = 1,
    ): Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun postStories(
        @Header("Authorization") Token: String,
        @Part file: MultipartBody.Part,
        @Part("description") desc: RequestBody
    ): Call<AddNewStoryResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body loginModel: LoginModel): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("register")
    fun register(@Body registerModel: RegisterModel): Call<RegisterModel>
}
