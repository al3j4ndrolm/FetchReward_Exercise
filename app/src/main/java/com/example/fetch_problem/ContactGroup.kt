package com.example.fetch_problem

/**
 * Represents a contact group with a unique ID, list ID, and an optional name.
 *
 * @property id The unique identifier for the contact group.
 * @property listId The ID associated with the list to which this contact group belongs.
 * @property name The optional name of the contact group.
 */
data class ContactGroup(
    val id: Int,
    val listId: Int,
    val name: String?,
)