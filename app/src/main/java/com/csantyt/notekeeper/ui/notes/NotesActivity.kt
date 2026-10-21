package com.csantyt.notekeeper.ui.notes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.csantyt.notekeeper.R
import com.csantyt.notekeeper.data.Session
import com.csantyt.notekeeper.databinding.ActivityNotesBinding
import com.csantyt.notekeeper.model.Note
import com.csantyt.notekeeper.ui.login.LoginActivity
import com.csantyt.notekeeper.viewmodel.NoteViewModel

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter
    private var allNotes: List<Note> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Session.userId(this) <= 0) {
            openLogin()
            return
        }

        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAdapter = NoteAdapter { note ->
            startActivity(
                Intent(this, NoteEditorActivity::class.java)
                    .putExtra(NoteEditorActivity.EXTRA_ID, note.id)
            )
        }
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = noteAdapter

        noteViewModel.notes.observe(this) { notes ->
            allNotes = notes
            showNotes()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) = showNotes()
        })

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, NoteEditorActivity::class.java))
        }

        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.action_logout) {
                Session.clear(this)
                openLogin()
                true
            } else {
                false
            }
        }
    }

    private fun showNotes() {
        val query = binding.etSearch.text?.toString().orEmpty().trim()
        val shown = if (query.isEmpty()) {
            allNotes
        } else {
            allNotes.filter { note ->
                note.title.contains(query, ignoreCase = true) ||
                    note.content.contains(query, ignoreCase = true)
            }
        }
        noteAdapter.submitList(shown)
        binding.tvEmpty.isVisible = shown.isEmpty()
        binding.tvEmpty.setText(if (query.isEmpty()) R.string.empty_notes else R.string.empty_search)
    }

    private fun openLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        )
        finish()
    }
}
