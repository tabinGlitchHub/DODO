package com.example.maroonmumbai.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.maroonmumbai.model.CategoryModelClass

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun post(category: CategoryModelClass): Long

    @Query("SELECT * FROM table_category")
    fun getAllCategories(): LiveData<List<CategoryModelClass>>

    @Delete
    suspend fun deleteCategory(category: CategoryModelClass)
}