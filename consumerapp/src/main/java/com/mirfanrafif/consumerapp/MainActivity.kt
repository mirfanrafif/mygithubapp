package com.mirfanrafif.consumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirfanrafif.consumerapp.DatabaseContract.FavouriteColumns.Companion.CONTENT_URI
import com.mirfanrafif.consumerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavouritesAdapter
    private lateinit var binding: ActivityMainBinding

    companion object {
        private var TAG = MainActivity::class.java.simpleName
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        Log.d(TAG, "onCreateView: ")
        adapter = FavouritesAdapter()
        adapter.notifyDataSetChanged()
        binding.rvListFavorite.adapter = adapter
        binding.rvListFavorite.layoutManager = LinearLayoutManager(this)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavourites()
            }
        }

        if (savedInstanceState == null) {
            loadFavourites()
        }else{
            savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)?.also { adapter.setData(it) }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
    }

    private fun loadFavourites() {
        Log.d(TAG, "loadFavourites: ")
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val deferredFavourites = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressBar.visibility = View.GONE
            val favourites = deferredFavourites.await()
            if (favourites.size > 0) {
                adapter.setData(favourites)
            }else{
                adapter.setData(favourites)
                Toast.makeText(this@MainActivity, "Tidak ada data untuk saat ini",
                    Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavourites)
    }
}