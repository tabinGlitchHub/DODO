package com.example.maroonmumbai.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.maroonmumbai.model.TodoModelClass

//Database name and version
const val DATABASE_NAME = "Dodo_database"
const val DATABASE_VERSION = 1

//Table names
const val TABLE_TODO = "todos"
const val TABLE_NOTE = "notes"
const val TABLE_REMINDER = "reminder"
const val TABLE_CATEGORIES = "categories"

//Common key names
const val KEY_ID = "id"
const val CATEGORY_ID = "catg_id"
const val CATEGORY_NAME = "catg_name"

//tasks keys
const val KEY_TASK_TITLE = "task_title"
const val KEY_TASK_DETAIL = "task_detail"
const val KEY_TASK_STATUS = "task_status"
const val KEY_TASK_LABEL = "task_label"

//Notes keys
const val KEY_NOTE_TITLE = "note_title"
const val KEY_NOTE_DETAIL = "note_detail"

//Reminder keys
const val KEY_REMINDER_TITLE = "rem_title"
const val KEY_REMINDER_DATETIME = "rem_date_time"
const val KEY_REMINDER_STATUS = "rem_status"

//create table commands
const val CREATE_TABLE_TODO = "CREATE TABLE $TABLE_TODO (" +
        "$KEY_ID INTEGER PRIMARY KEY, " +
        "$KEY_TASK_TITLE TEXT, " +
        "$KEY_TASK_DETAIL TEXT, " +
        "$KEY_TASK_STATUS INTEGER, " +
        "$KEY_TASK_LABEL INTEGER" +
        "$CATEGORY_ID INTEGER)"

const val CREATE_TABLE_NOTES = "CREATE TABLE $TABLE_NOTE (" +
        "$KEY_ID INTEGER PRIMARY KEY, " +
        "$KEY_NOTE_TITLE TEXT, " +
        "$KEY_NOTE_DETAIL TEXT" +
        "$CATEGORY_ID INTEGER)"

const val CREATE_TABLE_REMINDER = "CREATE TABLE $TABLE_REMINDER (" +
        "$KEY_ID INTEGER PRIMARY KEY, " +
        "$KEY_REMINDER_TITLE TEXT, " +
        "$CATEGORY_ID INTEGER" +
        "$KEY_REMINDER_DATETIME DATETIME" +
        "$KEY_REMINDER_STATUS INTEGER)"

const val CREATE_TABLE_CATEGORY = "CREATE TABLE $TABLE_CATEGORIES (" +
        "$KEY_ID INTEGER PRIMARY KEY, " +
        "$CATEGORY_NAME TEXT)"

const val DROP_TABLE_COMMAND = "DROP TABLE IF EXISTS"

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_TODO)
        db?.execSQL(CREATE_TABLE_NOTES)
        db?.execSQL(CREATE_TABLE_REMINDER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("$DROP_TABLE_COMMAND $TABLE_TODO")
        db?.execSQL("$DROP_TABLE_COMMAND $TABLE_NOTE")
        db?.execSQL("$DROP_TABLE_COMMAND $TABLE_REMINDER")
        db?.execSQL("$DROP_TABLE_COMMAND $TABLE_CATEGORIES")

        onCreate(db);
    }

    fun addATask(todo: TodoModelClass){
        val db: SQLiteDatabase = this.writableDatabase

        val status = if(todo.isDone) 1 else 0
        val value = ContentValues()
        value.put(KEY_TASK_TITLE, todo.title)
        value.put(KEY_TASK_DETAIL, todo.description)
        value.put(KEY_TASK_LABEL, todo.label)
        value.put(KEY_TASK_STATUS, status)

        db.insert(TABLE_TODO, null, value)
    }

}