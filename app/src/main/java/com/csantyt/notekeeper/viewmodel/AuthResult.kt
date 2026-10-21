package com.csantyt.notekeeper.viewmodel

sealed class AuthResult {
    data class Success(val userId: Int) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
