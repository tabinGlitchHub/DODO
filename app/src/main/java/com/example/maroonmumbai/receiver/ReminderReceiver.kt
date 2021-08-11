package com.example.maroonmumbai.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.example.maroonmumbai.R
import com.example.maroonmumbai.ui.activities.HomeActivity

class ReminderReceiver : BroadcastReceiver() {

    //when the intent is received after specified milliseconds has passed, do this
    override fun onReceive(context: Context, intent: Intent) {
        //receive extraString which was sent by service at the time of creation
        val message = intent.getStringExtra("EXTRA_EXACT_MESSAGE")
        when (intent.action) {
            "ACTION_SET_EXACT_ALARM" -> {
                buildNotification(
                    "DODO Reminder!",
                    message ?: "Tap to see more.",
                    context
                )
            }
        }
    }

    //build notification using 'NotificationCompat' builder
    private fun buildNotification(title: String, message: String, context: Context) {
        //this intent will load the app if notification is tapped on
        val intent = Intent(context, HomeActivity::class.java)
        //this extra bundle passes a string which will load the reminder fragment instead of default
        intent.putExtra("EXTRA_TRIGGER_REMINDER", "reminder")
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(context, "DODO_CHANNEL_ID")
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_alarm)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(0, notification)
    }

}