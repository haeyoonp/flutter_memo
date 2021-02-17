package com.example.memo

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

private const val TAG = "Note"

data class Note (
        var note_id: String? = null,
        var name: String? = null,
        var folder_id: String? = null,
        var contents: String? = null,
        var last_edit: Date? = null
)

class NoteListViewModel() : ViewModel() {

    val db = Firebase.firestore

    private var notesLiveData : MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()

    fun getNotes(): MutableLiveData<List<Note>> {
        return notesLiveData
    }

    fun filterNotes(folder_id: String?): MutableLiveData<List<Note>> {
        var noteList = mutableListOf<Note>()
        db.collection("notes").whereEqualTo("folder_id", folder_id).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val note = document.toObject(Note::class.java)
                        note.note_id = document.id
                        noteList.add(note)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                .addOnCompleteListener {
                    notesLiveData.postValue(noteList)
                }
        return notesLiveData
    }

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