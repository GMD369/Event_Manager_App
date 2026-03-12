package com.example.eventmanagerapp

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerEventType: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var ivProfile: ImageView
    private lateinit var cbTerms: CheckBox
    
    private var selectedDate: String = ""
    private var imageUri: Uri? = null

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            ivProfile.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registration_scroll_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etFullName = findViewById(R.id.et_full_name)
        etPhone = findViewById(R.id.et_phone)
        etEmail = findViewById(R.id.et_email)
        spinnerEventType = findViewById(R.id.spinner_event_type)
        tvSelectedDate = findViewById(R.id.tv_selected_date)
        rgGender = findViewById(R.id.rg_gender)
        ivProfile = findViewById(R.id.iv_profile_image)
        cbTerms = findViewById(R.id.cb_terms)

        findViewById<Button>(R.id.btn_pick_date).setOnClickListener {
            showDatePicker()
        }

        findViewById<Button>(R.id.btn_upload_image).setOnClickListener {
            getImage.launch("image/*")
        }

        findViewById<Button>(R.id.btn_submit).setOnClickListener {
            if (validateForm()) {
                showConfirmationDialog()
            }
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
            selectedDate = "$d/${m + 1}/$y"
            tvSelectedDate.text = selectedDate
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun validateForm(): Boolean {
        val name = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()

        if (name.isEmpty()) {
            etFullName.error = "Name is required"
            return false
        }
        if (phone.isEmpty()) {
            etPhone.error = "Phone number is required"
            return false
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Valid email is required"
            return false
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Registration")
            .setMessage("Are you sure you want to submit your registration?")
            .setPositiveButton("Yes") { _, _ ->
                navigateToConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun navigateToConfirmation() {
        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra("FULL_NAME", etFullName.text.toString())
            putExtra("PHONE", etPhone.text.toString())
            putExtra("EMAIL", etEmail.text.toString())
            putExtra("EVENT_TYPE", spinnerEventType.selectedItem.toString())
            putExtra("EVENT_DATE", selectedDate)
            
            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                "Not Specified"
            }
            putExtra("GENDER", gender)
            
            putExtra("IMAGE_URI", imageUri?.toString())
        }
        startActivity(intent)
    }
}