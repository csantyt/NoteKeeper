package com.csantyt.notekeeper.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.csantyt.notekeeper.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): Note?

    @Query("SELECT * FROM notes WHERE userId = :userId ORDER BY updatedAt DESC")
    fun observeNotes(userId: Int): Flow<List<Note>>
}
