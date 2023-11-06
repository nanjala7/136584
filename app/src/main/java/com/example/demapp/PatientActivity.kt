package com.example.demapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class PatientActivity : AppCompatActivity() {
    private lateinit var taskCard: CardView
    private lateinit var locationCard: CardView
    private lateinit var familyCard: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caregiver)
        taskCard = findViewById(R.id.taskCard)
        locationCard = findViewById(R.id.locationCard)
         familyCard = findViewById(R.id.familyCard)


        taskCard.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)

        }
        locationCard.setOnClickListener {
            val intent = Intent(this, LocationActivity::class.java)
            startActivity(intent)
        }
        familyCard.setOnClickListener {
            val intent = Intent(this, MemoryActivity::class.java)
            startActivity(intent)
        }
    }
}
