package com.mirfanrafif.mygithubapp.ui.activities

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.SectionAdapter
import com.mirfanrafif.mygithubapp.databases.DatabaseContract
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.FavouriteColumns.Companion.CONTENT_URI
import com.mirfanrafif.mygithubapp.databinding.ActivityDetailUserBinding
import com.mirfanrafif.mygithubapp.models.User

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        val TAB_TITLES = intArrayOf(R.string.followers_tab, R.string.following_tab)
        const val EXTRA_ID = "extra_id"
        private var TAG = DetailUserActivity::class.java.simpleName
    }

    private lateinit var viewModel: DetailUserViewModel
    private lateinit var binding: ActivityDetailUserBinding
    private var user: User? = null
    private var uriWithId: Uri? = null
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailUserViewModel::class.java)
        setContentView(binding.root)

        val intent = intent
        val username = intent.getStringExtra(EXTRA_USER) as String
        id = intent.getIntExtra(EXTRA_ID, 0)
        Log.d(TAG, "ID : $id")
        if (id > 0) {
            uriWithId = Uri.parse("$CONTENT_URI/$id")
        }

        binding.detailUserLoading.visibility = View.VISIBLE

        setData(username)
        initTabLayout(username)

//        set jika sudah favorit
        if (id > 0) binding.fabFavourites.setImageResource(R.drawable.ic_baseline_favorite_24)
        binding.fabFavourites.setOnClickListener {
            when(id) {
                0 ->  addFavourites()
                else -> removeFavourites()
            }
        }


        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail User"
    }

    fun initTabLayout(username: String) {
        //tab layout dan viewpager
        val sectionAdapter = SectionAdapter(this, username)
        binding.viewPager.adapter = sectionAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setData(username: String) {
        //set data
        viewModel.loadDetail(username)
        viewModel.getDetail().observe(this, {
            binding.tvUsernameLabel.text = it.login
            binding.tvNameLabel.text = it.name
            binding.tvRepoLabel.text = it.public_repos.toString()
            binding.tvFollowingLabel.text = it.following.toString()
            binding.tvFollowerLabel.text = it.followers.toString()
            binding.tvLocationCompanyLabel.text = it.location
            Glide.with(this).load(it.avatar_url).into(binding.imgDetailAvatar)
            binding.detailUserLoading.visibility = View.GONE
            user = it
        })
    }

    private fun addFavourites() {
        val values = ContentValues()
        values.put(DatabaseContract.FavouriteColumns.LOGIN, user?.login)
        values.put(DatabaseContract.FavouriteColumns.NAME, user?.name)
        values.put(DatabaseContract.FavouriteColumns.IMAGE_URL, user?.avatar_url)
        binding.fabFavourites.setImageResource(R.drawable.ic_baseline_favorite_24)

        uriWithId = contentResolver.insert(CONTENT_URI, values)
        id = uriWithId?.lastPathSegment?.toInt()?: 0
        Toast.makeText(this, "Berhasil ditambahkan ke favorit", Toast.LENGTH_LONG).show()
    }

    private fun removeFavourites() {
        uriWithId?.let { contentResolver.delete(it, null, null) }
        binding.fabFavourites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        uriWithId = null
        id = 0
        Toast.makeText(this, "Berhasil dihapus dari Favorit", Toast.LENGTH_SHORT).show()
    }
}