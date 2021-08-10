package com.example.maroonmumbai.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import io.karn.notify.Notify

class ReminderReceiver: BroadcastReceiver() {

    //when the intent is received after specified milliseconds has passed, do this
    override fun onReceive(context: Context, intent: Intent) {
        //receive extraString which was sent by service at the time of creation
        val message = intent.getStringExtra("EXTRA_EXACT_MESSAGE")
        when(intent.action){
            "ACTION_SET_EXACT_ALARM" -> {
                buildNotification(
                    "DODO Reminder",
                    message?:"Tap to see what it is!",
                    context
                )
            }
        }
    }

    //build notification using 'Notify' builder
    private fun buildNotification(title: String, message: String, context: Context){
        Notify
            .with(context)
            .content {
                this.title = title
                this.text = message
            }.show()
    }

}