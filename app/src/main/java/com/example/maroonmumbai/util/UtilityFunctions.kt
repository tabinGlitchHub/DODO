package com.example.maroonmumbai.util

import android.content.Context
import android.widget.Toast

class UtilityFunctions {

    public fun makeToast(message: String, context: Context, toastLength: Int){
        Toast.makeText(context, message, toastLength).show()
    }

}