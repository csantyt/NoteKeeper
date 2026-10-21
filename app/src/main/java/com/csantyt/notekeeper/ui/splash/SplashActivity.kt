package com.csantyt.notekeeper.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.csantyt.notekeeper.data.AppDatabase
import com.csantyt.notekeeper.data.Session
import com.csantyt.notekeeper.databinding.ActivitySplashBinding
import com.csantyt.notekeeper.ui.login.LoginActivity
import com.csantyt.notekeeper.ui.notes.NotesActivity
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK
        val bars = WindowInsetsControllerCompat(window, window.decorView)
        bars.hide(WindowInsetsCompat.Type.systemBars())
        bars.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressFill.pivotX = 0f
        binding.progressFill.scaleX = 0.08f
        binding.progressFill.animate()
            .scaleX(1f)
            .setDuration(1600)
            .withEndAction { openNext() }
            .start()
    }

    private fun openNext() {
        lifecycleScope.launch {
            val id = Session.userId(this@SplashActivity)
            val user = if (id > 0) {
                AppDatabase.getDatabase(applicationContext).userDao().getUserById(id)
            } else {
                null
            }
            if (user == null) {
                Session.clear(this@SplashActivity)
            }
            val destination = if (user != null) NotesActivity::class.java else LoginActivity::class.java
            startActivity(Intent(this@SplashActivity, destination))
            finish()
        }
    }
}
