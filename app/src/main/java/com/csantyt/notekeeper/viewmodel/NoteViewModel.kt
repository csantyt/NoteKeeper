package com.csantyt.notekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.csantyt.notekeeper.data.AppDatabase
import com.csantyt.notekeeper.data.Session
import com.csantyt.notekeeper.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val userId = Session.userId(application)
    private val noteDao = AppDatabase.getDatabase(application).noteDao()

    val notes = noteDao.observeNotes(userId).asLiveData()

    fun addNote(title: String, content: String) {
        if (userId <= 0) return
        viewModelScope.launch {
            noteDao.insert(Note(userId = userId, title = title, content = content))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    suspend fun getNote(id: Int): Note? {
        val note = noteDao.getNoteById(id) ?: return null
        return note.takeIf { it.userId == userId }
    }
}
