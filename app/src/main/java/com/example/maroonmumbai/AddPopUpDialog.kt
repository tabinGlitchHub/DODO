package com.example.maroonmumbai

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pop_up_dialog.*

class AddPopUpDialog: BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pop_up_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noteBSButton.setOnClickListener {
            Intent(activity, EditNoteActivity::class.java).also {
                startActivity(it)
            }
        }
        reminderBSButton.setOnClickListener {
            Intent(activity, EditReminderActivity::class.java).also {
                startActivity(it)
            }
        }
        taskBSButton.setOnClickListener {
            Intent(activity, EditTodoActivity::class.java).also {
                startActivity(it)
            }
        }
    }

}