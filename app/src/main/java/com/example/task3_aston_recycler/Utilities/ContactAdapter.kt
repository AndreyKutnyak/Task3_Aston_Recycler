package com.example.task3_aston_recycler.Utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task3_aston_recycler.Contact
import com.example.task3_aston_recycler.R

class ContactAdapter(private val onItemClickListener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var isSelectionMode: Boolean = false
    private var contactList: List<Contact> = emptyList()
    private var selectedItems: MutableSet<Int> = mutableSetOf()

    fun deleteSelectedItems() {
        val newList = contactList.toMutableList()
        val sortedSelectedItems = selectedItems.sortedDescending()
        sortedSelectedItems.forEach {
            newList.removeAt(it)
        }
        selectedItems.clear()
        submitList(newList)
    }
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun enableSelectionMode(enable: Boolean) {
        isSelectionMode = enable
        notifyDataSetChanged()
        if (isSelectionMode) {
            selectedItems.clear()
        }
    }

    fun toggleItemSelection(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.bind(contact, selectedItems.contains(position))
        holder.itemView.setBackgroundResource(if (selectedItems.contains(position)) R.color.selectedItemBackground else android.R.color.transparent)
        holder.itemView.isActivated = isSelectionMode && selectedItems.contains(position)
        holder.itemView.setOnClickListener {
            if (isSelectionMode) {
                toggleItemSelection(position)
            } else {
                onItemClickListener.invoke(contact)
            }
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstNameTextView: TextView = itemView.findViewById(R.id.tvFirstName)
        private val lastNameTextView: TextView = itemView.findViewById(R.id.tvLastName)
        private val phoneNumberTextView: TextView = itemView.findViewById(R.id.tvPhoneNumber)

        fun bind(contact: Contact, isSelected: Boolean) {
            firstNameTextView.text = contact.firstName
            lastNameTextView.text = contact.lastName
            phoneNumberTextView.text = contact.phoneNumber
            itemView.isActivated = isSelected
        }
    }

    fun submitList(newList: List<Contact>) {
        val diffResult = DiffUtil.calculateDiff(ContactDiffCallback(contactList, newList))
        contactList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}