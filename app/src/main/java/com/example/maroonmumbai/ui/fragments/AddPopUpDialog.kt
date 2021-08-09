package com.example.maroonmumbai.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.maroonmumbai.R
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.pop_up_dialog.*

class AddPopUpDialog() : BottomSheetDialogFragment() {

    val TAG = "Debug"
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
            val dialogFragment = EditNoteFragment()
            activity?.supportFragmentManager?.let { fragManager -> dialogFragment.show(fragManager, dialogFragment.tag) }

            this.dismiss()
        }
        reminderBSButton.setOnClickListener {
            val dialogFragment = EditReminderFragment()
            activity?.supportFragmentManager?.let { fragManager -> dialogFragment.show(fragManager, dialogFragment.tag) }

            this.dismiss()
        }
        taskBSButton.setOnClickListener {
            val dialogFragment = EditTodoFragment()
            activity?.supportFragmentManager?.let { fragManager -> dialogFragment.show(fragManager, dialogFragment.tag) }

            this.dismiss()
        }
    }

}