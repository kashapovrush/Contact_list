package com.kashapovrush.contactlist

data class User(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val deletionState: Boolean = false
)