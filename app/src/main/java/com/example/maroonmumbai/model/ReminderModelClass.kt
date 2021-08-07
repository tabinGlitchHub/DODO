package com.example.maroonmumbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_reminder"
)
data class ReminderModelClass(
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var title: String,
    var date: String,
    var time: String,
    var status: Boolean = false,
    var category: String?
)