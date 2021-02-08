package com.example.memo

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DbManager {

    val db = Firebase.firestore

    // Create a new user with a first and last name
    val folder = hashMapOf(
        "name" to "all",
        "num_notes" to 0
    )
}
