package com.example.eventmanagerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val name = intent.getStringExtra("FULL_NAME")
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val eventType = intent.getStringExtra("EVENT_TYPE")
        val eventDate = intent.getStringExtra("EVENT_DATE")
        val gender = intent.getStringExtra("GENDER")
        val imageUriString = intent.getStringExtra("IMAGE_URI")

        findViewById<TextView>(R.id.tv_summary_name).text = "Full Name: $name"
        findViewById<TextView>(R.id.tv_summary_phone).text = "Phone Number: $phone"
        findViewById<TextView>(R.id.tv_summary_email).text = "Email: $email"
        findViewById<TextView>(R.id.tv_summary_event_type).text = "Event Type: $eventType"
        findViewById<TextView>(R.id.tv_summary_event_date).text = "Event Date: $eventDate"
        findViewById<TextView>(R.id.tv_summary_gender).text = "Gender: $gender"

        val ivSummaryImage = findViewById<ImageView>(R.id.iv_summary_image)
        if (imageUriString != null) {
            ivSummaryImage.setImageURI(Uri.parse(imageUriString))
        }

        findViewById<Button>(R.id.btn_back_home).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}