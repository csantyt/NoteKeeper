package com.csantyt.notekeeper.data

import android.content.Context

object Session {
    private const val PREFS = "note_keeper_session"
    private const val KEY_USER_ID = "user_id"

    fun userId(context: Context): Int {
        return context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(KEY_USER_ID, 0)
    }

    fun save(context: Context, userId: Int) {
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_USER_ID, userId)
            .apply()
    }

    fun clear(context: Context) {
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_USER_ID)
            .apply()
    }
}
