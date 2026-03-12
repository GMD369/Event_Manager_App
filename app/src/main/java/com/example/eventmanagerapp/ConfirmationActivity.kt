package com.example.eventmanagerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmation)

        // Handle system bars padding
        val container = findViewById<LinearLayout>(R.id.confirmation_container)
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
        if (!imageUriString.isNullOrEmpty()) {
            try {
                ivSummaryImage.setImageURI(Uri.parse(imageUriString))
            } catch (e: Exception) {
                ivSummaryImage.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }

        findViewById<Button>(R.id.btn_back_home).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}