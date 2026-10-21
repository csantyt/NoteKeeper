package com.tuapp.notekeper.ui.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tuapp.notekeper.R
import com.tuapp.notekeper.data.Session
import com.tuapp.notekeper.databinding.ActivityNoteEditorBinding
import com.tuapp.notekeper.model.Note
import com.tuapp.notekeper.ui.login.LoginActivity
import com.tuapp.notekeper.viewmodel.NoteViewModel
import kotlinx.coroutines.launch

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private var noteId: Int = 0
    private var existing: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Session.userId(this) <= 0) {
            startActivity(
                Intent(this, LoginActivity::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            )
            finish()
            return
        }

        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra(EXTRA_ID, 0)
        binding.toolbar.title = getString(if (noteId == 0) R.string.new_note else R.string.edit_note)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnDelete.isVisible = noteId != 0

        if (noteId != 0) {
            lifecycleScope.launch {
                val note = noteViewModel.getNote(noteId)
                if (note == null) {
                    Toast.makeText(this@NoteEditorActivity, R.string.note_missing, Toast.LENGTH_SHORT).show()
                    finish()
                    return@launch
                }
                existing = note
                binding.etTitle.setText(note.title)
                binding.etContent.setText(note.content)
            }
        }

        binding.btnSave.setOnClickListener { saveNote() }
        binding.btnDelete.setOnClickListener { confirmDelete() }
    }

    private fun saveNote() {
        val title = binding.etTitle.text.toString().trim()
        val content = binding.etContent.text.toString().trim()
        if (title.isEmpty()) {
            binding.etTitle.error = getString(R.string.title_required)
            return
        }
        val current = existing
        if (current == null) {
            noteViewModel.addNote(title, content)
        } else {
            noteViewModel.updateNote(
                current.copy(
                    title = title,
                    content = content,
                    updatedAt = System.currentTimeMillis()
                )
            )
        }
        Toast.makeText(this, R.string.note_saved, Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun confirmDelete() {
        val note = existing ?: return
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.delete_title)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                noteViewModel.deleteNote(note)
                Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    companion object {
        const val EXTRA_ID = "note_id"
    }
}
