package com.example.maroonmumbai.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maroonmumbai.model.NotesModelClass

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(note: NotesModelClass): Long

    @Query("SELECT * FROM table_notes")
    fun getAllNotes(): LiveData<List<NotesModelClass>>

    @Delete
    suspend fun deleteNote(note: NotesModelClass)
}