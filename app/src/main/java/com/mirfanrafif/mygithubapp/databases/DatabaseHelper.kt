package com.mirfanrafif.mygithubapp.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mirfanrafif.mygithubapp.databases.DatabaseContract.FavouriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbgithubapp"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_FAVOURITES = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavouriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavouriteColumns.LOGIN} TEXT NOT NULL," +
                " ${DatabaseContract.FavouriteColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavouriteColumns.IMAGE_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVOURITES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}