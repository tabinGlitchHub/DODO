package com.example.maroonmumbai

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.pop_up_dialog.*

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

        // TODO: 7/29/21 implement sqlite

        fab.setOnClickListener {
            val modalbottomSheetFragment = AddPopUpDialog()
            modalbottomSheetFragment.show(supportFragmentManager, modalbottomSheetFragment.tag)
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