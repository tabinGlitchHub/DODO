package com.example.maroonmumbai.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.maroonmumbai.receiver.ReminderReceiver

class ReminderService(private val context: Context) {

    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    //set non repetitive alarms
    fun setExactAlarm(timeInMillis: Long, message: String) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = "ACTION_SET_EXACT_ALARM"
                    putExtra("EXTRA_EXACT_ALARM_TIME", timeInMillis)
                    putExtra("EXTRA_EXACT_MESSAGE", message)
                })
        )
    }

    //repetitive alarms, todo: implement these
    fun setWeeklyAlarm(timeInMillis: Long) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = "ACTION_SET_REPETITIVE_ALARM"
                    putExtra("EXTRA_EXACT_ALARM_TIME", timeInMillis)
                })
        )
    }
    fun setDailyAlarm(timeInMillis: Long) {

    }

    //use this to set reminder/alarm, note that 'setExactAndAllowWhileIdle' only works for android M
    //and above sdkversions (which is current minSdk anyway) to support versions below M use 'setExact'
    //the difference is in the 'Doze mode' functionality, read more on documentation
    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {

        alarmManager?.let {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }

    private fun getIntent(): Intent = Intent(context, ReminderReceiver::class.java)

    private fun getPendingIntent(intent: Intent): PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

}