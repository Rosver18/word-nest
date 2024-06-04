package com.example.mydictionaryapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val mainAppButton = findViewById<Button>(R.id.button_main_app)
        val goToAboutButton = findViewById<Button>(R.id.button_about_app)

        mainAppButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        goToAboutButton.setOnClickListener {
            val intent = Intent(this, AboutMain::class.java)
            startActivity(intent)
        }
    }
}