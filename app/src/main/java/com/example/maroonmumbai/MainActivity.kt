package com.example.maroonmumbai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_todo.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBarTxtTodo = "Manage your Tasks!"
        val toolBarTxtReminder = "Manage your Time!"
        val toolBarTxtNotes = "Manage your Notes!"
        val toolBarTxtClipB = "Manage your Clips!"

        bottomNavBar.menu.getItem(2).isEnabled = false
        val toDoFragment = ToDoFragment()
        val notesFragment = NotesFragment()
        val reminderFragment = ReminderFragment()
        val clipBoardFragment = ClipBoardFragment()

        setCurrentFragmentTo(toDoFragment)
        toolBarText.text = toolBarTxtTodo

        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tasksToDoItem -> {
                    setCurrentFragmentTo(toDoFragment)
                    toolBarText.text = toolBarTxtTodo
                }
                R.id.NotesItem -> {
                    setCurrentFragmentTo(notesFragment)
                    toolBarText.text = toolBarTxtNotes
                }
                R.id.ReminderItem -> {
                    setCurrentFragmentTo(reminderFragment)
                    toolBarText.text = toolBarTxtReminder
                }
                R.id.ClipboardItem -> {
                    setCurrentFragmentTo(clipBoardFragment)
                    toolBarText.text = toolBarTxtClipB
                }
            }
            true
        }
    }

    // for switching between fragments when clicked on icons from bottom nav bar
    private fun setCurrentFragmentTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentFrame, fragment)
            commit()
        }
    }
}