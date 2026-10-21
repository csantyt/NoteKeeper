package com.tuapp.notekeper.ui.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.tuapp.notekeper.R
import com.tuapp.notekeper.data.Session
import com.tuapp.notekeper.databinding.ActivityLoginBinding
import com.tuapp.notekeper.model.User
import com.tuapp.notekeper.ui.notes.NotesActivity
import com.tuapp.notekeper.util.UserAvatars
import com.tuapp.notekeper.viewmodel.AuthResult
import com.tuapp.notekeper.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var accountAdapter: UserAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyDarkStatusBar()

        accountAdapter = UserAccountAdapter { user ->
            binding.etUsername.setText(user.username)
            binding.etPassword.requestFocus()
            showUserPortrait(user)
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = accountAdapter

        loginViewModel.registeredUsers.observe(this) { users ->
            accountAdapter.submitList(users)
            val empty = users.isEmpty()
            binding.rvUsers.isVisible = !empty
            binding.tvEmptyUsers.isVisible = empty
            binding.tvAccounts.text = if (empty) {
                getString(R.string.no_accounts)
            } else {
                resources.getQuantityString(R.plurals.registered_accounts, users.size, users.size)
            }
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString()
            ) { result ->
                when (result) {
                    is AuthResult.Success -> openNotes(result.userId)
                    is AuthResult.Error ->
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnGoRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showUserPortrait(user: User) {
        val radius = 60f * resources.displayMetrics.density
        binding.logo.setImageResource(UserAvatars.forUser(user.id))
        binding.logo.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.logo.strokeWidth = 3f * resources.displayMetrics.density
        binding.logo.strokeColor = ColorStateList.valueOf(getColor(R.color.yellow))
        binding.logo.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()
    }

    private fun openNotes(userId: Int) {
        Session.save(this, userId)
        startActivity(Intent(this, NotesActivity::class.java))
        finish()
    }

    private fun applyDarkStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = getColor(R.color.dark)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }
}
