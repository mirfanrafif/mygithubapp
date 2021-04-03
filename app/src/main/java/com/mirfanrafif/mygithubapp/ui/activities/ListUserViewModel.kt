package com.mirfanrafif.mygithubapp.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mirfanrafif.mygithubapp.api.ApiClient
import com.mirfanrafif.mygithubapp.models.SearchResult
import com.mirfanrafif.mygithubapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListUserViewModel: ViewModel() {
    private var listUser = MutableLiveData<ArrayList<User>>()
    private var TAG = ListUserViewModel::class.java.simpleName

    fun searchUser(query: String) {
        ApiClient().getUserService().searchUser(query).enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                val data = response.body()
                try {
                    listUser.postValue(data?.items)
                    Log.d(TAG, "onSuccess: Berhasil")
                }catch (e: Exception) {
                    Log.e(TAG, "onSuccess: Gagal ${e.message}")
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getListUser() : LiveData<ArrayList<User>>{
        return listUser
    }
}