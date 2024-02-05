package com.example.task3_aston_recycler

data class Contact(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var selected: Boolean = false
)