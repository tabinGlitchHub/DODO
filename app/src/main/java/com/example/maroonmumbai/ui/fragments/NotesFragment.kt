package com.example.maroonmumbai.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maroonmumbai.adapter.NotesAdapter
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.R
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment: Fragment(R.layout.fragment_notes) {

    lateinit var viewModel: HomeViewModel
    private lateinit var notesAdapter: NotesAdapter
    private var notesList = ArrayList<NotesModelClass>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // attach viewmodel in context to HomeActivity
        viewModel = (activity as HomeActivity).viewModel

        setUpRecyclerView()

        //access all existing tasks from db and observe them
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer {
            notesList = it as ArrayList<NotesModelClass>
            notesAdapter.differ.submitList(it)
        })
    }

    private fun setUpRecyclerView(){
        notesAdapter = NotesAdapter(this)
        val width = resources.configuration.screenWidthDp
        rvNotes.also {
            it.adapter = notesAdapter
            //this recyclerview implements grid layout but the grid size is dependant on screen
            //size, so the grid is set accordingly
            it.layoutManager = GridLayoutManager(activity, getGridCount(width), RecyclerView.VERTICAL, false)
        }
    }

    private fun getGridCount(screenWidth: Int): Int{
        return screenWidth / 190
    }
}