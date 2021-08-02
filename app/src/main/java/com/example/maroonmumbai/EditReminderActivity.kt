package com.example.maroonmumbai

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_reminder.*
import java.util.*

class EditReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reminder)

        btnSetDate.setOnClickListener { view -> setDate(view) }
        btnSetTime.setOnClickListener { view -> setTime(view) }

    }

    private fun setTime(view: View?) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR)
        val currentMinute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { view, hourOfDay, minute ->
            var hour = hourOfDay
            var amOrPm = ""
            hour = if(hour > 12) {
                amOrPm = "pm"
                hour - 12
            } else {
                amOrPm = "am"
                hour
            }
            val displayText = "$hour:$minute $amOrPm"
            tvTime.text = displayText
        },currentHour, currentMinute, false).show()
    }

    private fun setDate(view: View?) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { view, selectedYear, selectedMonth, selectedDayOfMonth ->
            val displayText = "$selectedDayOfMonth-${selectedMonth+1}-$selectedYear"
            tvDate.text = displayText
        }, currentYear, currentMonth, currentDay).show()
    }
}