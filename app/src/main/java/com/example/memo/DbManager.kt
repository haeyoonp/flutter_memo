package com.example.memo

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class DbManager {

    //var db = FirebaseFirestore.getInstance()
    private val mDatabase: DatabaseReference? = null
    var db = FirebaseDatabase.getInstance().getReference();

    // Create a new user with a first and last name
    val folder = hashMapOf(
        "name" to "all",
        "num_notes" to 0
    )




}
