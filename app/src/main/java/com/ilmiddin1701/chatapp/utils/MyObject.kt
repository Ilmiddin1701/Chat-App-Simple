package com.ilmiddin1701.chatapp.utils

import com.google.firebase.database.FirebaseDatabase

object MyObject {
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var reference = firebaseDatabase.getReference("users")
}