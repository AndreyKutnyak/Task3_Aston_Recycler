package com.example.task3_aston_recycler.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task3_aston_recycler.Utilities.ContactAdapter
import com.example.task3_aston_recycler.ContactRepository
import com.example.task3_aston_recycler.R

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var deleteButtonsContainer: LinearLayout
    private lateinit var btnCancel: Button
    private lateinit var btnDelete: Button
    private var isSelectionMode: Boolean = false
    private var selectedItems: MutableSet<Int> = mutableSetOf()
    private lateinit var toolbar: Toolbar
    private lateinit var contactAdapter: ContactAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_main)
        val actionDelete: MenuItem = toolbar.menu.findItem(R.id.action_delete_main)
        actionDelete.setOnMenuItemClickListener {
            toggleSelectionMode()
            contactAdapter.deleteSelectedItems()
            true
        }
        ContactRepository.contactsList()
        contactAdapter = ContactAdapter { contact ->
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contactId", contact.id)
            startActivity(intent)
        }
        recyclerView.adapter = contactAdapter
        ContactRepository.initAdapter(contactAdapter)
        val contactsList = ContactRepository.getContacts()

        contactAdapter.submitList(contactsList)
        addButton = findViewById(R.id.addButton)
        deleteButtonsContainer = findViewById(R.id.deleteButtonsContainer)
        btnCancel = findViewById(R.id.btnCancel)
        btnDelete = findViewById(R.id.btnDelete)
        addButton.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener {
            toggleSelectionMode()
        }
        btnDelete.setOnClickListener {
            contactAdapter.deleteSelectedItems()
            toggleSelectionMode()
        }
    }
    private fun toggleSelectionMode() {
        isSelectionMode = !isSelectionMode

        if (isSelectionMode) {
            addButton.visibility = View.GONE
            deleteButtonsContainer.visibility = View.VISIBLE
        } else {
            addButton.visibility = View.VISIBLE
            deleteButtonsContainer.visibility = View.GONE
            selectedItems.clear()
        }
        contactAdapter.enableSelectionMode(isSelectionMode)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cancel -> {
                toggleSelectionMode()
                return true
            }
            R.id.action_delete -> {
                contactAdapter.deleteSelectedItems()
                toggleSelectionMode()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}