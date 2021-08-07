package com.example.maroonmumbai.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.model.TodoModelClass
import com.example.maroonmumbai.repository.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    //Live data lists to restore data after activity killing event
    val todoList: MutableLiveData<ArrayList<TodoModelClass>> = MutableLiveData()
    val noteList: MutableLiveData<ArrayList<NotesModelClass>> = MutableLiveData()
    val reminderList: MutableLiveData<ArrayList<ReminderModelClass>> = MutableLiveData()

    //----------------------GET, INSERT, DELETE TODOS----------------------
    fun getAllTodos() = homeRepository.getAllTodos()

    fun insertTodo(todo: TodoModelClass) = viewModelScope.launch { homeRepository.postTodo(todo) }

    fun deleteTodo(todo: TodoModelClass) = viewModelScope.launch { homeRepository.deleteTodo(todo) }

    fun setTodoDone(id:Int) = viewModelScope.launch { homeRepository.setTodoDone(id) }

    fun setTodoNotDone(id:Int) = viewModelScope.launch { homeRepository.setTodoNotDone(id) }

    //----------------------GET, INSERT, DELETE NOTES----------------------
    fun getAllNotes() = homeRepository.getAllNotes()

    fun insertNote(note: NotesModelClass) = viewModelScope.launch { homeRepository.postNote(note) }

    fun deleteNote(note: NotesModelClass) =
        viewModelScope.launch { homeRepository.deleteNote(note) }

    //----------------------GET, INSERT, DELETE REMINDER----------------------
    fun getAllReminders() = homeRepository.getAllReminders()

    fun insertReminder(reminder: ReminderModelClass) =
        viewModelScope.launch { homeRepository.postReminder(reminder) }

    fun deleteReminder(reminder: ReminderModelClass) =
        viewModelScope.launch { homeRepository.deleteReminder(reminder) }

}