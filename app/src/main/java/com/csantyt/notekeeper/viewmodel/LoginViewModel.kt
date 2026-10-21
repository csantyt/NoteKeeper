package com.csantyt.notekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.csantyt.notekeeper.data.AppDatabase
import com.csantyt.notekeeper.model.User
import com.csantyt.notekeeper.util.PasswordHasher
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    val registeredUsers = userDao.observeUsers().asLiveData()

    fun login(username: String, password: String, onResult: (AuthResult) -> Unit) {
        viewModelScope.launch {
            val name = username.trim()
            if (name.isEmpty() || password.isEmpty()) {
                onResult(AuthResult.Error("Completa usuario y contraseña"))
                return@launch
            }
            val user = userDao.getUserByUsername(name)
            if (user != null && user.password == PasswordHasher.hash(password)) {
                onResult(AuthResult.Success(user.id))
            } else {
                onResult(AuthResult.Error("Usuario o contraseña incorrectos"))
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
            when {
                name.length < 3 ->
                    onResult(AuthResult.Error("El usuario debe tener al menos 3 caracteres"))
                password.length < 4 ->
                    onResult(AuthResult.Error("La contraseña debe tener al menos 4 caracteres"))
                password != confirm ->
                    onResult(AuthResult.Error("Las contraseñas no coinciden"))
                userDao.getUserByUsername(name) != null ->
                    onResult(AuthResult.Error("Ese usuario ya existe"))
                else -> {
                    val id = userDao.insertUser(
                        User(username = name, password = PasswordHasher.hash(password))
                    ).toInt()
                    onResult(AuthResult.Success(id))
                }
            }
        }
    }
}
