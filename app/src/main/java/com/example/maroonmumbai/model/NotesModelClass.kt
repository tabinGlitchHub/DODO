package com.example.maroonmumbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_notes"
)
data class NotesModelClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String,
    var details: String,
    var category: String?)