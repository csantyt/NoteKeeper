package com.csantyt.notekeeper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.csantyt.notekeeper.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users ORDER BY username COLLATE NOCASE ASC")
    fun observeUsers(): Flow<List<User>>
}
