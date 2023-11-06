package com.example.demapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class RoleActivity : AppCompatActivity() {


    private lateinit var caregiverCard: CardView
    private lateinit var patientCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        caregiverCard = findViewById(R.id.caregiverCard)
        patientCard = findViewById(R.id.patientCard)
        caregiverCard.setOnClickListener {
            val intent = Intent(this, CaregiverActivity::class.java)
            startActivity(intent)
        }
        patientCard.setOnClickListener {
            val intent = Intent(this, PatientActivity::class.java)
            startActivity(intent)
        }
    }
}
