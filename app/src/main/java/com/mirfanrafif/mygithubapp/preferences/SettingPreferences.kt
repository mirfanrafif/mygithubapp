package com.mirfanrafif.mygithubapp.preferences

import android.content.Context
import androidx.core.content.edit

class SettingPreferences(context: Context) {
    companion object {
        const val PREFS_NAME = "settings"
        const val REMINDER = "reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getReminder(): Boolean = preferences.getBoolean(REMINDER, false)

    fun setReminder(reminder: Boolean) {
        preferences.edit {
            putBoolean(REMINDER, reminder)
        }
    }
}