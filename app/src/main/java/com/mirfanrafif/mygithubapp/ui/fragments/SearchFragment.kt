package com.mirfanrafif.mygithubapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mirfanrafif.mygithubapp.ListUserAdapter
import com.mirfanrafif.mygithubapp.R
import com.mirfanrafif.mygithubapp.databinding.FragmentSearchBinding
import com.mirfanrafif.mygithubapp.ui.activities.ListUserViewModel

class SearchFragment : Fragment() {
    companion object {
        private const val USERNAME = "username"
        @JvmStatic
        fun newInstance(username: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
    }

    private lateinit var listUserViewModel: ListUserViewModel
    private var username: String? = null
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ListUserViewModel::class.java)

        binding.searchProgressBar.visibility = View.VISIBLE
        username?.let { listUserViewModel.searchUser(it) }


        val adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvListUser.adapter = adapter
        binding.rvListUser.layoutManager = LinearLayoutManager(context)

        listUserViewModel.getListUser().observe(viewLifecycleOwner, {
            adapter.setData(it)
            binding.tvWelcome.text = resources.getQuantityString(R.plurals.show_data, it.size, it.size)
            binding.searchProgressBar.visibility = View.GONE
        })
    }
}