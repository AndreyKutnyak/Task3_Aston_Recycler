package com.example.task3_aston_recycler.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task3_aston_recycler.Utilities.ContactAdapter
import com.example.task3_aston_recycler.ContactRepository
import com.example.task3_aston_recycler.R

class AddContactActivity : AppCompatActivity() {

    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactAdapter = ContactAdapter {}
        contactAdapter.submitList(ContactRepository.getContacts())
        setContentView(R.layout.activity_add_contact)
        val buttonAddContact: Button = findViewById(R.id.buttonAddContact)
        buttonAddContact.setOnClickListener {
            addContact()
        }
    }


    private fun addContact() {
        val editTextFirstName: EditText = findViewById(R.id.editTextFirstName)
        val editTextLastName: EditText = findViewById(R.id.editTextLastName)
        val editTextPhoneNumber: EditText = findViewById(R.id.editTextPhoneNumber)

        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val phoneNumber = editTextPhoneNumber.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
            ContactRepository.addContact(firstName, lastName, phoneNumber)

            // Уведомляем адаптер о изменениях


            finish()
        } else {
            Toast.makeText(this, getString(R.string.toastEmptyAddContact), Toast.LENGTH_SHORT).show()
        }
    }
}