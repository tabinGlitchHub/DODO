package com.example.maroonmumbai.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.TodoModelClass
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.util.UtilityFunctions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_edit_todo.*
import kotlinx.android.synthetic.main.dialog_edit_todo.view.*
import kotlinx.android.synthetic.main.model_category_chips.*
import kotlinx.android.synthetic.main.model_category_chips.view.*

class EditTodoFragment : DialogFragment(R.layout.dialog_edit_todo) {

    private lateinit var viewModel: HomeViewModel
    private var category: String? = null
    private val util = UtilityFunctions()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = (activity as HomeActivity).viewModel

        val dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_todo, null)

        dialog.chipPersonal.setOnClickListener { category = "Personal" }
        dialog.chipEducation.setOnClickListener { category = "Education" }
        dialog.chipEntertainment.setOnClickListener { category = "Entertainment" }
        dialog.chipFitness.setOnClickListener { category = "Fitness" }
        dialog.chipGroceries.setOnClickListener { category = "Groceries" }
        dialog.chipHome.setOnClickListener { category = "Home" }
        dialog.chipOther.setOnClickListener { category = null }
        dialog.chipShopping.setOnClickListener { category = "Shopping" }
        dialog.chipWork.setOnClickListener { category = "Work" }

        //on Submit collect this information from dialog
        dialog.fabSetTask.setOnClickListener {
            //collect user input about priority from radio-group
            val label = when (dialog.radioGrpTodo.checkedRadioButtonId) {
                dialog.radioBtnHigh.id -> "H"
                dialog.radioBtnMedium.id -> "M"
                else -> "L"
            }
            //create a model from user input fields
            val todo = TodoModelClass(
                null,
                dialog.edtTxtTaskTitle.text.toString(),
                dialog.edtTxtTaskDetails.text.toString(),
                false,
                label,
                category
            )
            //insert the model in the database with viewmodel
            viewModel.insertTodo(todo)
            //dismiss after done
            this.dismiss()
            util.makeToast("Task added successfully!", activity as HomeActivity, Toast.LENGTH_LONG)
        }
        // create a materialdialog using styles for rounded corner and inflate dialog
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)
            .setView(dialog).create()
    }
}