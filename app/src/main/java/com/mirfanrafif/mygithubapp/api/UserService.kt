package com.mirfanrafif.mygithubapp.api

import com.mirfanrafif.mygithubapp.models.SearchResult
import com.mirfanrafif.mygithubapp.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<SearchResult>

    @GET("users/{username}")
    fun getUsersByUsername(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<User>>
}