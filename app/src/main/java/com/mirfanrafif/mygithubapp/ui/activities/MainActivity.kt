package com.mirfanrafif.mygithubapp.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.ui.fragments.FavouritesFragment
import com.mirfanrafif.mygithubapp.ui.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var listUserViewModel: ListUserViewModel
    private lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)

        mFragmentManager = supportFragmentManager
        val favouritesFragment = FavouritesFragment()
        mFragmentManager.commit {
            add(R.id.frame_container, favouritesFragment, FavouritesFragment::class.java.simpleName)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val searchFragment = SearchFragment.newInstance(query)
                mFragmentManager.commit {
                    replace(R.id.frame_container, searchFragment, SearchFragment::class.java.simpleName)
                    addToBackStack(null)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return true
    }
}