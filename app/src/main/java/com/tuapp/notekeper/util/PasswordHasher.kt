package com.tuapp.notekeper.util

import java.security.MessageDigest

object PasswordHasher {
    fun hash(raw: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(raw.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
