package com.example.maroonmumbai.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_category"
)
data class CategoryModelClass(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var categoryName: String
)