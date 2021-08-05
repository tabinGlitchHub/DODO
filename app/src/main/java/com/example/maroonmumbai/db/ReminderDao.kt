package com.example.maroonmumbai.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maroonmumbai.model.ReminderModelClass

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(reminder: ReminderModelClass): Long

    @Query("SELECT * FROM table_reminder")
    fun getAllReminder(): LiveData<List<ReminderModelClass>>

    @Delete
    suspend fun deleteReminder(reminder: ReminderModelClass)
}