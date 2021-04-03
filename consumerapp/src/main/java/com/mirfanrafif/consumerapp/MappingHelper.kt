package com.mirfanrafif.consumerapp

import android.database.Cursor

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
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOGIN))
            val description = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
            val imageUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.IMAGE_URL))
            favourites = Favorite(id, title, description, imageUrl)
        }
        return favourites
    }
}