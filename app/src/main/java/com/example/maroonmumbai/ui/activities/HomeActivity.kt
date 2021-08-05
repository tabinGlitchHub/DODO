package com.example.maroonmumbai.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.maroonmumbai.R
import com.example.maroonmumbai.db.MainDatabase
import com.example.maroonmumbai.repository.HomeRepository
import com.example.maroonmumbai.ui.fragments.*
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.ui.viewmodels.HomeViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //repository is the connection to database which in turn has access to the DAOs
        //this is also where the database is first instantiated or connected depending on the instance
        val homeRepository = HomeRepository(MainDatabase(this))
        val vmProviderFactory = HomeViewModelProviderFactory(homeRepository)
        //viewmodel is obtained from viewmodel provider factory which connects it to the
        //viewmodel class
        viewModel = ViewModelProvider(this,vmProviderFactory).get(HomeViewModel::class.java)

        //constant strings
        val toolBarTxtTodo = "Manage your Tasks!"
        val toolBarTxtReminder = "Manage your Time!"
        val toolBarTxtNotes = "Manage your Notes!"
        val toolBarTxtClipB = "Manage your Clips!"

        //instances of fragments to load
        val toDoFragment = ToDoFragment()
        val notesFragment = NotesFragment()
        val reminderFragment = ReminderFragment()
        val clipBoardFragment = ClipBoardFragment()

        bottomNavBar.menu.getItem(2).isEnabled = false

        //initialize frame layout by default to-do fragment
        setCurrentFragmentTo(toDoFragment)
        toolBarText.text = toolBarTxtTodo

        //switch fragments depending on the item clicked from bottom nav bar
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

        //onclick listener for the '+' fab, this pops up a bottom sheet fragment for user to choose
        fab.setOnClickListener {
            val modalBottomSheetFragment = AddPopUpDialog()
            modalBottomSheetFragment.show(supportFragmentManager, modalBottomSheetFragment.tag)
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