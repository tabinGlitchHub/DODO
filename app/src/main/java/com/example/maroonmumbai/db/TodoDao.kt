package com.example.maroonmumbai.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maroonmumbai.model.TodoModelClass

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(todo: TodoModelClass): Long

    @Query("SELECT * FROM table_todos")
    fun getAllTodos(): LiveData<List<TodoModelClass>>

    @Delete
    suspend fun deleteTodo(todo: TodoModelClass)
}