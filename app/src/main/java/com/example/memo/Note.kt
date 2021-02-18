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

    private val NOTE_REF = Firebase.firestore.collection("notes")
    /*
    private val foldersLiveData : MutableLiveData<List<Folder>> by lazy {
        MutableLiveData<List<Folder>>().also {
            loadFolders()
        }
    }//= MutableLiveData<List<Folder>>()*/

    private var notesLiveData : MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
    val selectedNote = MutableLiveData<Note>()

    fun getNotes(): MutableLiveData<List<Note>> {
        return notesLiveData
    }

    fun filterNotes(folder_id: String?): MutableLiveData<List<Note>> {
        var noteList = mutableListOf<Note>()
        NOTE_REF.whereEqualTo("folder_id", folder_id).get()
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

    fun selectNote(item: Note) {
        selectedNote.postValue(item)
    }

    fun insertNote(note_id: String?, name: String?, folder_id: String?, contents: String?) {
        Log.d(TAG, "insertNote $folder_id")
        if (folder_id == null) {
            return
        }

        val newNote = hashMapOf(
                "name" to name,
                "folder_id" to folder_id,
                "contents" to contents
        )
        NOTE_REF
                .add(newNote)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

    }
}