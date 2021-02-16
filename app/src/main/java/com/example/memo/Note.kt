package com.example.memo

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Note"

data class Note (
        var note_id: String? = null,
        var name: String? = null,
        var folder_id: String? = null,
        var contents: String? = null
)

class NoteListViewModel() : ViewModel() {

    val db = Firebase.firestore

    private var notesLiveData : MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()

    init {
        var noteList = mutableListOf<Note>()
        db.collection("folders").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val note = document.toObject(Note::class.java)
                        noteList.add(note)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                .addOnCompleteListener {
                    Log.d(TAG, "noteList : ${noteList}")
                    Log.d(TAG, "addOnCompleteListener noteList : ${notesLiveData}")
                    notesLiveData.setValue(noteList)
                }

    }

    fun getNotes(): MutableLiveData<List<Note>> {
        Log.d(TAG, "getNotes() : $notesLiveData")
        return notesLiveData
    }


    /* If the name and description are present, create new Flower and add it to the datasource */
    fun insertNote(note_id: String?, name: String?, folder_id: String?, contents: String?) {
        if (name == null) {
            return
        }

        //val image = dataSource.getRandomFlowerImageAsset()
        val newFolder = Note(
                note_id,
                name,
                folder_id,
                contents
        )

    }
}