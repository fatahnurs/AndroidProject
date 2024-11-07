package com.example.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)

        editText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Aksi yang ingin dilakukan ketika tombol enter ditekan
                handleEnterKeyPressed()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun handleEnterKeyPressed() {
        // Logika atau aksi yang ingin dilakukan ketika tombol enter ditekan
        val inputText = editText.text.toString()
        // Contoh: Tampilkan teks di Toast
        Toast.makeText(this, "Tombol Enter ditekan! Input: $inputText", Toast.LENGTH_SHORT).show()
    }
}