package com.csantyt.notekeeper.util

import com.csantyt.notekeeper.R

object UserAvatars {
    private val portraits = intArrayOf(
        R.drawable.avatar_01,
        R.drawable.avatar_02,
        R.drawable.avatar_03,
        R.drawable.avatar_04,
        R.drawable.avatar_05,
        R.drawable.avatar_06
    )

    fun forUser(userId: Int): Int {
        val index = (userId - 1).coerceAtLeast(0) % portraits.size
        return portraits[index]
    }
}
