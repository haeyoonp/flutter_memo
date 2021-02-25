package com.example.memo.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

private const val TAG = "Note"

@Entity//(tableName = "folder")
data class NoteR(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "note_name") val noteName: String?,
        @ColumnInfo(name = "note_contents") val noteContents: String?,
        @ColumnInfo(name = "folder_id") val folderID: String?,
        @ColumnInfo(name = "last_edit") val lastEdit: Date?
)

data class Note (
        var note_id: String? = null,
        var name: String? = null,
        var folder_id: String? = null,
        var contents: String? = null,
        var last_edit: Date? = null
)

class NoteListViewModel() : ViewModel() {

    private val NOTE_REF = Firebase.firestore.collection("notes")
    private var notesLiveData : MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
    val selectedNote : MutableLiveData<Note> = MutableLiveData<Note>()//Note()


    init{
        NOTE_REF.addSnapshotListener{ snapshot, e ->
            Log.d(TAG, "NOTE_REF.addSnapshotListener")
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                getNotes()
            }
        }
    }

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

    fun selectNote(note: Note) {

        var findNote : Note =
            Note()
        if(note.note_id == null){
            Log.d(TAG, "if(note.note_id == null)")
            if(note.folder_id == null){
                Log.d(TAG, "note.folder_id == null")
                findNote.folder_id = "default"
            }else{
                Log.d(TAG, "else folder_id")
                findNote.folder_id = note.folder_id
            }
            selectedNote.setValue(findNote)
        }else{
            note.note_id?.let { NOTE_REF.document(it).get()
                    .addOnSuccessListener { document ->
                        findNote = document.toObject(Note::class.java)!!
                        findNote.note_id = note.note_id
                    }
                    .addOnCompleteListener {
                        selectedNote.setValue(findNote)
                    }
            }
        }
    }

    fun saveNote(note: Note?) {
        Log.d(TAG, "saveNote $note")

        var newNote : Note? = note
        if (newNote != null) {
            newNote.folder_id = newNote.folder_id?: "default"
            Log.d(TAG, "saveNote ${newNote.folder_id} ")
            /*
            if (newNote.folder_id == null) {
                newNote.folder_id = "default"
            }*/
            newNote.note_id?.let { NOTE_REF.document(it).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            //NOTE_REF.document(it).set(newNote)
                            Log.d(TAG, "DocumentSnapshot data: ${newNote}")
                        } else {
                            Log.d(TAG, "No such document")
                        }
                        NOTE_REF.document(it).set(newNote)
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }?:{
                Log.d(TAG, "NOTE_REF.add(newNote) $newNote")
                NOTE_REF.add(newNote)
            }()
        }

    }

    //deleteNote(note_id: String?){}
    //deleteNotes(folder_id: String?){}

}