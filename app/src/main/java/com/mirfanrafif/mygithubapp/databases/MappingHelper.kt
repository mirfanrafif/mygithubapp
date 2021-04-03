package com.mirfanrafif.mygithubapp.databases

import android.database.Cursor
import com.mirfanrafif.mygithubapp.models.FavouritesItem

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<FavouritesItem> {
        val notesList = ArrayList<FavouritesItem>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOGIN))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
                val imageUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.IMAGE_URL))
                notesList.add(FavouritesItem(id, title, description, imageUrl))
            }
        }
        return notesList
    }

    fun mapCursorToObject(notesCursor: Cursor?): FavouritesItem {
        var favourites = FavouritesItem()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.LOGIN))
            val description = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.NAME))
            val imageUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.IMAGE_URL))
            favourites = FavouritesItem(id, title, description, imageUrl)
        }
        return favourites
    }
}