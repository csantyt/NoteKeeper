package com.tuapp.notekeper.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.tuapp.notekeper.R
import com.tuapp.notekeper.data.Session
import com.tuapp.notekeper.databinding.ActivityRegisterBinding
import com.tuapp.notekeper.ui.notes.NotesActivity
import com.tuapp.notekeper.viewmodel.AuthResult
import com.tuapp.notekeper.viewmodel.LoginViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = getColor(R.color.dark)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        binding.btnRegister.setOnClickListener {
            loginViewModel.register(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString(),
                confirm = binding.etConfirm.text.toString()
            ) { result ->
                when (result) {
                    is AuthResult.Success -> {
                        Session.save(this, result.userId)
                        startActivity(Intent(this, NotesActivity::class.java))
                        finish()
                    }
                    is AuthResult.Error ->
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnHaveAccount.setOnClickListener { finish() }
    }
}
