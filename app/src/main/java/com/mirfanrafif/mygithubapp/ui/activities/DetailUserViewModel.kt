package com.mirfanrafif.mygithubapp.ui.activities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mirfanrafif.mygithubapp.api.ApiClient
import com.mirfanrafif.mygithubapp.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel(){
    private val user = MutableLiveData<User>()
    private val followers = MutableLiveData<ArrayList<User>>()
    private val following = MutableLiveData<ArrayList<User>>()

    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

    fun loadDetail(username: String) {
        ApiClient().getUserService().getUsersByUsername(username).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val data = response.body() as User
                try {
                    user.postValue(data)
                    Log.d("DetailUserViewModel", "onSuccess: Berhasil mengambil data ${data.login}")
                }catch (e: Exception) {
                    e.printStackTrace()
                    Log.d("DetailUserViewModel", "onSuccess: gagal ${e.message}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("DetailUserViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun loadFollowers(username: String) {
        ApiClient().getUserService().getFollowers(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                val data = response.body() as ArrayList<User>
                try {
                    followers.postValue(data)
                }catch (e: Exception) {
                    Log.d(TAG, "onSuccess: Gagal ${e.message}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun loadFollowing(username: String) {
        ApiClient().getUserService().getFollowing(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                val data = response.body() as ArrayList<User>
                try {
                    following.postValue(data)
                }catch (e: Exception) {
                    Log.d(TAG, "onSuccess: Gagal ${e.message}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getDetail() : LiveData<User> {
        return user
    }

    fun getFollowers() : LiveData<ArrayList<User>>{
        return followers
    }

    fun getFollowing() : LiveData<ArrayList<User>>{
        return following
    }
}