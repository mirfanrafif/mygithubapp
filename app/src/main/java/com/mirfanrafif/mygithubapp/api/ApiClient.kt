package com.mirfanrafif.mygithubapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getUserService() : UserService{
        return getRetrofit().create(UserService::class.java)
    }
}