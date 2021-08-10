package com.example.maroonmumbai.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.maroonmumbai.R
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.service.ReminderService
import com.example.maroonmumbai.ui.activities.HomeActivity
import com.example.maroonmumbai.ui.viewmodels.HomeViewModel
import com.example.maroonmumbai.util.UtilityFunctions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_edit_reminder.view.*
import kotlinx.android.synthetic.main.model_category_chips.view.*
import java.util.*

class EditReminderFragment : DialogFragment(R.layout.dialog_edit_reminder) {

    private lateinit var viewModel: HomeViewModel
    private lateinit var reminderService: ReminderService
    //inputs for reminder model
    private var inTitle: String? = null
    private var inDate: String? = null
    private var inTime: String? = null
    private var inCategory: String? = null
    //Textview instance to display final date and time in String
    private lateinit var finalTextView: TextView
    private val util = UtilityFunctions()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        viewModel = (activity as HomeActivity).viewModel
        //create a service instance, will be used for setting up notification
        reminderService = ReminderService(activity as HomeActivity)

        val dialog = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_reminder, null)

        finalTextView = dialog.tvDisplayReminderDate

        dialog.also {
            it.chipPersonal.setOnClickListener { inCategory = "Personal" }
            it.chipEducation.setOnClickListener { inCategory = "Education" }
            it.chipEntertainment.setOnClickListener { inCategory = "Entertainment" }
            it.chipFitness.setOnClickListener { inCategory = "Fitness" }
            it.chipGroceries.setOnClickListener { inCategory = "Groceries" }
            it.chipHome.setOnClickListener { inCategory = "Home" }
            it.chipOther.setOnClickListener { inCategory = null }
            it.chipShopping.setOnClickListener { inCategory = "Shopping" }
            it.chipWork.setOnClickListener { inCategory = "Work" }
        }

        //textwatcher to update 'inTitle' without needing to press final submit button
        //without this, the 'btnEditDateTime' button would input inTitle as null always
        dialog.edtTxtReminderTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inTitle = s.toString()
            }

        })

        //check if 'inTitle' is null and warn user to fill it first, to prevent null value field,
        //this is done because this string will be passed to service and then from service to
        // receiver, receiver will then display this String in the title of Notification
        dialog.btnEditDateTime.setOnClickListener {
            if(inTitle == null){
                util.makeToast(
                    "Please enter a Title first!",
                    activity as HomeActivity,
                    Toast.LENGTH_SHORT
                )
            }else{
                setDateTime { timeInMillis ->
                    reminderService.setExactAlarm(timeInMillis, inTitle.toString())
                }
            }
        }

        //on Submit collect this information from dialog
        dialog.fabSetReminder.setOnClickListener {
            //create a model from user input fields
            val reminder = ReminderModelClass(
                null,
                inTitle.toString(),
                inDate.toString(),
                inTime.toString(),
                true,
                inCategory
            )
            if (inTitle != null && inDate != null && inTime != null) {
                //insert the model in the database with viewmodel
                viewModel.insertReminder(reminder)
                //dismiss after done
                this.dismiss()
                util.makeToast(
                    "Reminder added successfully!",
                    activity as HomeActivity,
                    Toast.LENGTH_LONG
                )
            } else {
                util.makeToast(
                    "Please fill all the details",
                    activity as HomeActivity,
                    Toast.LENGTH_LONG
                )
            }
        }
        // create a materialdialog using styles for rounded corner and inflate dialog
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_rounded)
            .setView(dialog).create()
    }

    // Take Date and Time inputs together from the user this makes it easier to calculate
    // milliseconds which in turn helps in setting a service with fixed millisecond
    private fun setDateTime(callBack: (Long) -> Unit) {
        Calendar.getInstance().apply {
            //set second and millisecond to 0, not doing this would consider the very moment
            //this function was called which necessarily won't be 0. this acts as an exact marker
            //for millisecond calculation from minute to minute
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)

            //First let user pick date from DatePickerDialog then time from TimePickerDialog
            // in this order
            DatePickerDialog(
                activity as HomeActivity, { view, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)

                    TimePickerDialog(
                        activity as HomeActivity, { view, hour, min ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, min)

                            // formatting of 24 hour to 12 hour format and getting am/pm, this could
                            //be done with an external formatter but i like the work
                            val amOrPm: String
                            val formatHour: Int
                            if (hour > 12) {
                                formatHour = hour - 12
                                amOrPm = "pm"
                            } else {
                                formatHour = hour
                                amOrPm = "am"
                            }
                            val txtDate = "${doubleDigitNum(day)}-${doubleDigitNum(month)}-$year"
                            val txtTime =
                                "${doubleDigitNum(formatHour)}:${doubleDigitNum(min)}$amOrPm"
                            val displayText = "Reminder set for, $txtDate on $txtTime."
                            //collect time and date which will be filled in the reminder model
                            // on Submission
                            inDate = txtDate
                            inTime = txtTime
                            finalTextView.text = displayText
                            callBack(this.timeInMillis)
                        }, this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                }, this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    //to TimePickerDialog and DatePickerDialog return a simple Integer, this function converts
    //single digit integers to double digits for aesthetic purposes.
    //eg. 1 -> 01, 9 -> 09, 12 -> 12
    private fun doubleDigitNum(input: Int): String {
        if (input < 10) {
            return "0$input"
        }
        return input.toString()
    }
}