package com.mirfanrafif.mygithubapp.models

data class SearchResult(
    val incomplete_results: Boolean,
    val items: ArrayList<User>,
    val total_count: Int
)