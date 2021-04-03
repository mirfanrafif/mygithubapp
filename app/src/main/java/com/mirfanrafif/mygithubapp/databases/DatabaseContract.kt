package com.mirfanrafif.mygithubapp.databases

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.mirfanrafif.mygithubapp"
    const val SCHEME = "content"

    internal class FavouriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favourites"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val NAME = "name"
            const val IMAGE_URL = "image_url"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}