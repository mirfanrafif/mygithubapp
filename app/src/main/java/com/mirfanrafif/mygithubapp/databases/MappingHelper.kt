package com.mirfanrafif.mygithubapp.databases

import android.database.Cursor
import com.mirfanrafif.mygithubapp.models.Favorite

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Favorite> {
        val notesList = ArrayList<Favorite>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOGIN))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
                val imageUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.IMAGE_URL))
                notesList.add(Favorite(id, title, description, imageUrl))
            }
        }
        return notesList
    }

    fun mapCursorToObject(notesCursor: Cursor?): Favorite {
        var favourites = Favorite()
        if (notesCursor != null) {
            if (notesCursor.count > 0) {
                notesCursor.apply {
                    moveToFirst()
                    val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                    val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOGIN))
                    val description = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
                    val imageUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.IMAGE_URL))
                    favourites = Favorite(id, title, description, imageUrl)
                }
            }
        }
        return favourites
    }
}