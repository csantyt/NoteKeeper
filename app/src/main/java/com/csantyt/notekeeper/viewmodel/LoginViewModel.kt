package com.csantyt.notekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.csantyt.notekeeper.data.AppDatabase
import com.csantyt.notekeeper.model.User
import com.csantyt.notekeeper.util.AuthValidator
import com.csantyt.notekeeper.util.PasswordHasher
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    val registeredUsers = userDao.observeUsers().asLiveData()

    fun login(username: String, password: String, onResult: (AuthResult) -> Unit) {
        viewModelScope.launch {
            val name = username.trim()
            val error = AuthValidator.loginError(username, password)
            if (error != null) {
                onResult(AuthResult.Error(error))
                return@launch
            }
            val user = userDao.getUserByUsername(name)
            if (user != null && AuthValidator.passwordMatches(user.password, password)) {
                onResult(AuthResult.Success(user.id))
            } else {
                onResult(AuthResult.Error(AuthValidator.LOGIN_WRONG))
            }
        }
    }

    fun register(
        username: String,
        password: String,
        confirm: String,
        onResult: (AuthResult) -> Unit
    ) {
        viewModelScope.launch {
            val name = username.trim()
            val formatError = AuthValidator.registerError(username, password, confirm, usernameTaken = false)
            if (formatError != null) {
                onResult(AuthResult.Error(formatError))
                return@launch
            }
            if (userDao.getUserByUsername(name) != null) {
                onResult(AuthResult.Error(AuthValidator.USER_TAKEN))
                return@launch
            }
            val id = userDao.insertUser(
                User(username = name, password = PasswordHasher.hash(password))
            ).toInt()
            onResult(AuthResult.Success(id))
        }
    }
}
