package com.mirfanrafif.mygithubapp.ui.fragments

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirfanrafif.mygithubapp.FavouritesAdapter
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.FavouriteColumns.Companion.CONTENT_URI
import com.mirfanrafif.mygithubapp.databases.MappingHelper
import com.mirfanrafif.mygithubapp.databinding.FragmentFavouritesBinding
import com.mirfanrafif.mygithubapp.models.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var adapter: FavouritesAdapter

    companion object {
        private var TAG = FavouritesFragment::class.java.simpleName
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(layoutInflater, container, false)
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        Log.d(TAG, "onCreateView: ")
        adapter = FavouritesAdapter()
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

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

        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavourites)
    }

    private fun loadFavourites() {
        Log.d(TAG, "loadFavourites: ")
        GlobalScope.launch(Dispatchers.Main) {
            binding.loadFavouritesProgress.visibility = View.VISIBLE
            val deferredFavourites = async(Dispatchers.IO) {
                val cursor = activity?.contentResolver?.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.loadFavouritesProgress.visibility = View.GONE
            val favourites = deferredFavourites.await()
            if (favourites.size > 0) {
                adapter.setData(favourites)
            }else{
                adapter.setData(favourites)
                Toast.makeText(context, "Tidak ada data untuk saat ini",
                    Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
        }
    }

}