package com.example.tmrpert12

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.tmrpert12.Database.DBHelper
import com.example.tmrpert12.Model.Note


class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        // Tampilkan semua catatan saat aplikasi dimulai
        displayNotes()

        val edtId = findViewById<EditText>(R.id.edtId)
        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtContent = findViewById<EditText>(R.id.edtContent)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        btnAdd.setOnClickListener {
            // Tambah catatan baru
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()
            dbHelper.addNote(Note(0, title, content))
            displayNotes()
            clearFields()
        }

        btnUpdate.setOnClickListener {
            // Perbarui catatan
            val id = edtId.text.toString().toLong()
            val title = edtTitle.text.toString()
            val content = edtContent.text.toString()
            dbHelper.updateNote(Note(id, title, content))
            displayNotes()
            clearFields()
        }

        btnDelete.setOnClickListener {
            // Hapus catatan
            val id = edtId.text.toString().toLong()
            dbHelper.deleteNoteById(id)
            displayNotes()
            clearFields()
        }
    }

    private fun displayNotes() {
        val notes = dbHelper.getAllNotes()
        val notesString = StringBuilder()
        for (note in notes) {
            notesString.append("${note.id} | ${note.title} | ${note.content}\n")
        }
        val txtNotes = findViewById<TextView>(R.id.txtNotes)
        txtNotes.text = notesString.toString()
    }

    private fun clearFields() {
        val edtId = findViewById<EditText>(R.id.edtId)
        val edtTitle = findViewById<EditText>(R.id.edtTitle)
        val edtContent = findViewById<EditText>(R.id.edtContent)

        edtId.text.clear()
        edtTitle.text.clear()
        edtContent.text.clear()
    }
}
