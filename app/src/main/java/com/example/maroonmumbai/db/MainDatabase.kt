package com.example.maroonmumbai.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.maroonmumbai.model.CategoryModelClass
import com.example.maroonmumbai.model.NotesModelClass
import com.example.maroonmumbai.model.ReminderModelClass
import com.example.maroonmumbai.model.TodoModelClass

@Database(
    entities = [
        NotesModelClass::class,
        TodoModelClass::class,
        ReminderModelClass::class,
        CategoryModelClass::class
    ],
    version = 1
)
abstract class MainDatabase: RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getTodoDao(): TodoDao
    abstract fun getReminderDao(): ReminderDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {

        @Volatile
        private var instance: MainDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MainDatabase::class.java,
                "main_database.db"
            ).build()
    }

}