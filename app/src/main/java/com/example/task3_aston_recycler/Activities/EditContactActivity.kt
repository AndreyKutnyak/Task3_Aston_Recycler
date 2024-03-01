package com.example.task3_aston_recycler.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task3_aston_recycler.ContactRepository
import com.example.task3_aston_recycler.R
import com.example.task3_aston_recycler.Utilities.ContactAdapter


class EditContactActivity : AppCompatActivity() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonUpdateContact: Button
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonUpdateContact = findViewById(R.id.buttonUpdateContact)
        buttonUpdateContact.setOnClickListener {
           updateContact()
        }

        contactId = intent.getIntExtra("contactId", -1)
        loadContactData()
    }

    private fun loadContactData() {
        val contact = ContactRepository.getContactById(contactId)
        if (contact != null) {
            editTextFirstName.setText(contact.firstName)
            editTextLastName.setText(contact.lastName)
            editTextPhoneNumber.setText(contact.phoneNumber)
        } else {
            Toast.makeText(this, getString(R.string.ContactNotFound), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateContact() {
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val phoneNumber = editTextPhoneNumber.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
            ContactRepository.updateContact(contactId, firstName, lastName, phoneNumber)

            val intent = Intent()
            intent.putExtra("contactUpdated", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.toastEmptyAddContact), Toast.LENGTH_SHORT).show()
        }
    }
}