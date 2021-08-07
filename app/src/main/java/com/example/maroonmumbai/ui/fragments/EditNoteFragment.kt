package com.example.maroonmumbai.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maroonmumbai.R
import com.example.maroonmumbai.adapter.NotesAdapter
import com.example.maroonmumbai.adapter.TodoAdapter
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.util.UtilityFunctions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_edit_note.view.*
import kotlinx.android.synthetic.main.dialog_edit_note.view.fabSetNote
import kotlinx.android.synthetic.main.dialog_edit_todo.*
import kotlinx.android.synthetic.main.dialog_edit_todo.view.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.model_category_chips.view.*

class EditNoteFragment: DialogFragment(R.layout.dialog_edit_note){

    private lateinit var viewModel: HomeViewModel
    private var category: String? = null
    //utilFunction class is a custom class to store regularly used and universally application functions
    private val util = UtilityFunctions()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = (activity as HomeActivity).viewModel

        val dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_note, null)

        dialog.chipPersonal.setOnClickListener { category = "Personal" }
        dialog.chipEducation.setOnClickListener { category = "Education" }
        dialog.chipEntertainment.setOnClickListener { category = "Entertainment" }
        dialog.chipFitness.setOnClickListener { category = "Fitness" }
        dialog.chipGroceries.setOnClickListener { category = "Groceries" }
        dialog.chipHome.setOnClickListener { category = "Home" }
        dialog.chipOther.setOnClickListener { category = null }
        dialog.chipShopping.setOnClickListener { category = "Shopping" }
        dialog.chipWork.setOnClickListener { category = "Work" }

        dialog.fabSetNote.setOnClickListener {
            //collect user input about the note to be entered
            val note = NotesModelClass(
                null,
                dialog.edtTxtNoteTitle.text.toString(),
                dialog.edtTxtNoteDetails.text.toString(),
                category
            )
            //insert note to the db through viewmodel
            viewModel.insertNote(note)
            //dismiss dialog after done
            this.dismiss()
            util.makeToast("Note added successfully!", activity as HomeActivity,Toast.LENGTH_LONG)
        }
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)
            .setView(dialog).create()
    }
}