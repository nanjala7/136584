package com.example.demapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class CaregiverActivity : AppCompatActivity() {
    private lateinit var taskCard: CardView
    private lateinit var locationCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver)
        taskCard = findViewById(R.id.taskCard)
        locationCard = findViewById(R.id.locationCard)

        taskCard.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)

        }
        locationCard.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }
    }
}