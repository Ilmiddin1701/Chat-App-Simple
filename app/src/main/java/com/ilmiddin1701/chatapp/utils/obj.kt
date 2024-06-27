package com.ilmiddin1701.chatapp.utils

import com.google.firebase.database.FirebaseDatabase

object obj {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val reference = firebaseDatabase.getReference("users")
}