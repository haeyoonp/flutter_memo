package com.example.memo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val TAG = "Folder"

data class Folder (
        var id: String? = null,
        var name: String? = null,
        var number_notes: Int? = null
)

class FolderListViewModel : ViewModel() {

    private val FOLDER_REF = Firebase.firestore.collection("folders")
    private val foldersLiveData : MutableLiveData<List<Folder>> by lazy {
        MutableLiveData<List<Folder>>().also {
            loadFolders()
        }
    }//= MutableLiveData<List<Folder>>()
    val selectedFolder = MutableLiveData<Folder>()

    init{
        FOLDER_REF.addSnapshotListener{ snapshot, e ->
            Log.d(TAG, "FOLDER_REF.addSnapshotListener")
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                loadFolders()
            }
        }
    }

    fun getFolders(): MutableLiveData<List<Folder>> {
        return foldersLiveData
    }

    private fun loadFolders() {
        var folderList = mutableListOf<Folder>()

        FOLDER_REF.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val folder = document.toObject(Folder::class.java)
                        folder.id = document.id
                        folderList.add(folder)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
                .addOnCompleteListener {
                    foldersLiveData.setValue(folderList)//setValue
                }
    }

    fun selectFolder(item: Folder) {

        selectedFolder.postValue(item)
    }

    fun insertFolder(folderName: String?, notesNumber: Int?) {
        Log.d(TAG, "insertFolder $folderName")
        if (folderName == null) {
            return
        }
        val folder = hashMapOf("name" to folderName, "number_notes" to 0)
        FOLDER_REF
                .add(folder)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
    }

    fun deleteFolder(folder_id: String?) {
        Log.d(TAG, "deleteFolder $folder_id")
        //deleteNotes()
        //deleteFolder()

    }

}
