package com.example.mydictionaryapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AboutMain : AppCompatActivity() {

    private val peopleDetails = mapOf(
        "Rosver" to "\"what the balls\"",
        "Manly" to "\"I don't know why its bugged\"",
        "Elizah" to "\"I'm just looking for someone who can match my freak\""
    )

    private lateinit var selectedPerson: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_main)

        val spinner = findViewById<Spinner>(R.id.people_spinner)
        val showDetailsButton = findViewById<Button>(R.id.show_details_button)

        val backToMainMenuButton = findViewById<Button>(R.id.button_back_to_menu)
        backToMainMenuButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.people_names,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedPerson = parent.getItemAtPosition(position) as String
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedPerson = ""
            }
        }

        showDetailsButton.setOnClickListener {
            if (::selectedPerson.isInitialized && selectedPerson.isNotEmpty()) {
                showPersonDetails(selectedPerson)
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("No Selection")
                builder.setMessage("Please select a person from the list.")
                builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                builder.show()
            }
        }
    }

    private fun showPersonDetails(person: String) {
        val details = peopleDetails[person]
        val builder = AlertDialog.Builder(this)
        builder.setTitle(person)
        builder.setMessage(details)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }
}