package com.example.maroonmumbai.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.util.UtilityFunctions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_edit_reminder.view.*
import kotlinx.android.synthetic.main.model_category_chips.view.*
import java.util.*

class EditReminderFragment : DialogFragment(R.layout.dialog_edit_reminder) {

    private lateinit var viewModel: HomeViewModel
    private var category: String? = null
    private var date: String? = null
    private var time: String? = null
    private val util = UtilityFunctions()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        viewModel = (activity as HomeActivity).viewModel

        val dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_reminder, null)

        dialog.also {
            it.chipPersonal.setOnClickListener { category = "Personal" }
            it.chipEducation.setOnClickListener { category = "Education" }
            it.chipEntertainment.setOnClickListener { category = "Entertainment" }
            it.chipFitness.setOnClickListener { category = "Fitness" }
            it.chipGroceries.setOnClickListener { category = "Groceries" }
            it.chipHome.setOnClickListener { category = "Home" }
            it.chipOther.setOnClickListener { category = null }
            it.chipShopping.setOnClickListener { category = "Shopping" }
            it.chipWork.setOnClickListener { category = "Work" }
        }

        dialog.btnEditDate.setOnClickListener { setDate(dialog.tvDate) }

        dialog.btnEditTime.setOnClickListener { setTime(dialog.tvTime) }

        //on Submit collect this information from dialog
        dialog.fabSetReminder.setOnClickListener {
            //create a model from user input fields
            val reminder = ReminderModelClass(
                null,
                dialog.edtTxtReminderTitle.text.toString(),
                date.toString(),
                time.toString(),
                true,
                category
            )
            //insert the model in the database with viewmodel
            viewModel.insertReminder(reminder)
            //dismiss after done
            this.dismiss()
            util.makeToast(
                "Reminder added successfully!",
                activity as HomeActivity,
                Toast.LENGTH_LONG
            )
        }
        // create a materialdialog using styles for rounded corner and inflate dialog
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)
            .setView(dialog).create()
    }

    private fun setTime(tv: TextView) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR)
        val currentMinute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(activity, { view, hourOfDay, minute ->
            var hour = hourOfDay
            var amOrPm = ""
            hour = if (hour > 12) {
                amOrPm = "pm"
                hour - 12
            } else {
                amOrPm = "am"
                hour
            }
            // ------stored as hh:mm xm ---- string will be of length 8 always
            time = "${doubleDigitNum(hour)}:${doubleDigitNum(minute)} $amOrPm"
            tv.text = time
        }, currentHour, currentMinute, false).show()
    }

    private fun setDate(tv: TextView) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        activity?.let {
            DatePickerDialog(it, { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val day = doubleDigitNum(selectedDayOfMonth)
                val month = doubleDigitNum(selectedMonth + 1)
                date = "$day-$month-$selectedYear"
                tv.text = date
            }, currentYear, currentMonth, currentDay).show()
        }
    }

    private fun doubleDigitNum(input: Int): String {
        if (input < 10) {
            return "0$input"
        }
        return input.toString()
    }
}