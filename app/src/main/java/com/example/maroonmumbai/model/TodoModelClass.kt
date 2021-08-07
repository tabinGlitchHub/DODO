package com.example.maroonmumbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_todos"
)
data class TodoModelClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title:String,
    var description: String?,
    var isDone: Boolean = false,
    var label: String = "L",
    var category: String?)