package com.csantyt.notekeeper.util

import com.csantyt.notekeeper.model.Note

object NoteSearch {
    fun filter(notes: List<Note>, query: String): List<Note> {
        val text = query.trim()
        if (text.isEmpty()) return notes
        return notes.filter { note ->
            note.title.contains(text, ignoreCase = true) ||
                note.content.contains(text, ignoreCase = true)
        }
    }
}
