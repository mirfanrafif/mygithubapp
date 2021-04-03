package com.mirfanrafif.mygithubapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirfanrafif.mygithubapp.ListUserAdapter
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.databinding.FragmentFollowersBinding
import com.mirfanrafif.mygithubapp.ui.activities.DetailUserViewModel

class FollowersFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_TYPE = "extra_type"
    }

    private val bundle : Bundle? = null
    private lateinit var followersRv : RecyclerView
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersRv = view.findViewById(R.id.followersRv)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        val type = arguments?.get(EXTRA_TYPE) as String
        val username = arguments?.get(EXTRA_USER) as String
        val followersAdapter = ListUserAdapter()
        val followingAdapter = ListUserAdapter()

        when(type) {
            "followers" -> {
                binding.followersRv.adapter = followersAdapter
                binding.followersRv.layoutManager = LinearLayoutManager(view.context)
                viewModel.loadFollowers(username)
                binding.followersLoading.visibility = View.VISIBLE
            }
            "following" -> {
                binding.followersRv.adapter = followingAdapter
                binding.followersRv.layoutManager = LinearLayoutManager(view.context)
                viewModel.loadFollowing(username)
                binding.followersLoading.visibility = View.VISIBLE
            }
        }

        viewModel.getFollowers().observe(viewLifecycleOwner, {
            followersAdapter.setData(it)
            binding.followersLoading.visibility = View.GONE
        })

        viewModel.getFollowing().observe(viewLifecycleOwner, {
            followingAdapter.setData(it)
            binding.followersLoading.visibility = View.GONE
        })


    }

}