package com.example.tmrpert12.Database

//class DBHelper {
//}

// src/main/java/com/example/myapp/database/DBHelper.kt
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tmrpert12.Model.Note

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "NotesDB"
        private const val TABLE_NAME = "notes"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME($KEY_ID INTEGER PRIMARY KEY, $KEY_TITLE TEXT, $KEY_CONTENT TEXT)")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(note: Note) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, note.title)
        values.put(KEY_CONTENT, note.content)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getNoteById(id: Long): Note? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_TITLE, KEY_CONTENT), "$KEY_ID=?", arrayOf(id.toString()), null, null, null, null)

        return if (cursor != null) {
            cursor.moveToFirst()
            val note = Note(
                cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
            )
            cursor.close()
            note
        } else {
            null
        }
    }

    @SuppressLint("Range")
    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: android.database.SQLException) {
            db.execSQL(selectQuery)
            return emptyList()
        }

        if (cursor.moveToFirst()) {
            do {
                val note = Note(
                    cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                    cursor.getString(cursor.getColumnIndex(KEY_CONTENT))
                )
                notesList.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notesList
    }

    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, note.title)
        values.put(KEY_CONTENT, note.content)

        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(note.id.toString()))
    }

    fun deleteNoteById(id: Long): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
    }
}
