package com.csantyt.notekeeper.util

object AuthValidator {
    const val LOGIN_EMPTY = "Completa usuario y contraseña"
    const val USER_SHORT = "El usuario debe tener al menos 3 caracteres"
    const val PASSWORD_SHORT = "La contraseña debe tener al menos 4 caracteres"
    const val PASSWORD_MISMATCH = "Las contraseñas no coinciden"
    const val USER_TAKEN = "Ese usuario ya existe"
    const val LOGIN_WRONG = "Usuario o contraseña incorrectos"

    fun loginError(username: String, password: String): String? {
        if (username.trim().isEmpty() || password.isEmpty()) return LOGIN_EMPTY
        return null
    }

    fun registerError(
        username: String,
        password: String,
        confirm: String,
        usernameTaken: Boolean
    ): String? {
        val name = username.trim()
        return when {
            name.length < 3 -> USER_SHORT
            password.length < 4 -> PASSWORD_SHORT
            password != confirm -> PASSWORD_MISMATCH
            usernameTaken -> USER_TAKEN
            else -> null
        }
    }

    fun passwordMatches(storedHash: String, rawPassword: String): Boolean {
        return storedHash == PasswordHasher.hash(rawPassword)
    }
}
