package com.example.maroonmumbai

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesFragment: Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val notesList = ArrayList<NotesModelClass>()
        notesList.add(NotesModelClass("Maths","Some complex maths formula that i don't understand", ""))
        notesList.add(NotesModelClass("Test","Some complex Tests", ""))
        notesList.add(NotesModelClass("Remember this","Some stuff", ""))
        notesList.add(NotesModelClass("Maths","Some complex maths formula that i don't understand", ""))
        notesList.add(NotesModelClass("Test","Some complex Tests", ""))
        notesList.add(NotesModelClass("Remember this","Some stuff", ""))

        val adapter = NotesAdapter(notesList)

        val width = resources.configuration.screenWidthDp

        rvNotes.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(activity, getGridCount(width), RecyclerView.VERTICAL, false)
        }
    }

    fun getGridCount(screenWidth: Int): Int{
        return screenWidth / 190
    }
}