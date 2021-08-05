package com.example.maroonmumbai.repository

import com.example.maroonmumbai.db.MainDatabase
import com.example.maroonmumbai.model.CategoryModelClass
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.model.TodoModelClass

class HomeRepository(
    private val db: MainDatabase
) {

    //post, fetch and delete TODOs
    suspend fun postTodo(todo: TodoModelClass) = db.getTodoDao().post(todo)
    fun getAllTodos() = db.getTodoDao().getAllTodos()
    suspend fun deleteTodo(todo: TodoModelClass) = db.getTodoDao().deleteTodo(todo)

    //post, fetch and delete Notes
    suspend fun postNote(note: NotesModelClass) = db.getNotesDao().post(note)
    fun getAllNotes() = db.getNotesDao().getAllNotes()
    suspend fun deleteNote(note: NotesModelClass) = db.getNotesDao().deleteNote(note)

    //post, fetch and delete Reminder
    suspend fun postReminder(reminder: ReminderModelClass) = db.getReminderDao().post(reminder)
    fun getAllReminders() = db.getReminderDao().getAllReminder()
    suspend fun deleteReminder(reminder: ReminderModelClass) = db.getReminderDao().deleteReminder(reminder)

    //post, fetch and delete Categories
    suspend fun postCategory(category: CategoryModelClass) = db.getCategoryDao().post(category)
    fun getAllCategories() = db.getCategoryDao().getAllCategories()
    suspend fun  deleteCategory(category: CategoryModelClass) = db.getCategoryDao().deleteCategory(category)

}