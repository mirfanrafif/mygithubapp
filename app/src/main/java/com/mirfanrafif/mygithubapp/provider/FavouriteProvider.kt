package com.mirfanrafif.mygithubapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.AUTHORITY
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.FavouriteColumns.Companion.CONTENT_URI
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.FavouriteColumns.Companion.TABLE_NAME
import com.mirfanrafif.mygithubapp.databases.FavouriteHelper

class FavouriteProvider: ContentProvider() {
    companion object {
        private const val FAVOURITE = 1
        private const val FAVOURITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favouriteHelper: FavouriteHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVOURITE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVOURITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        favouriteHelper = FavouriteHelper.getInstance(context as Context)
        favouriteHelper.open()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)) {
            FAVOURITE -> favouriteHelper.queryAll()
            FAVOURITE_ID -> favouriteHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(FAVOURITE) {
            sUriMatcher.match(uri) -> favouriteHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleted: Int = when(FAVOURITE_ID) {
            sUriMatcher.match(uri) -> favouriteHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val updated: Int = when(FAVOURITE_ID) {
            sUriMatcher.match(uri) -> favouriteHelper.update(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }
}