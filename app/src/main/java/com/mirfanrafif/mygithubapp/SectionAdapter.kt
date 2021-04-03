package com.mirfanrafif.mygithubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mirfanrafif.mygithubapp.ui.fragments.FollowersFragment

class SectionAdapter(activity: AppCompatActivity, val username: String): FragmentStateAdapter(activity)  {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment = FollowersFragment()
        when(position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString(FollowersFragment.EXTRA_USER, username)
                bundle.putString(FollowersFragment.EXTRA_TYPE, "followers")
                fragment.arguments = bundle
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString(FollowersFragment.EXTRA_USER, username)
                bundle.putString(FollowersFragment.EXTRA_TYPE, "following")
                fragment.arguments = bundle
            }
        }
        return fragment
    }
}