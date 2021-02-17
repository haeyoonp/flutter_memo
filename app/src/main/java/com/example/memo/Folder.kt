package com.example.memo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Folder"

data class Folder (
        var id: String? = null,
        var name: String? = null,
        var number_notes: Int? = null
)

class FolderListViewModel : ViewModel() {

    val db = Firebase.firestore
    private var foldersLiveData : MutableLiveData<List<Folder>> = MutableLiveData<List<Folder>>()
    val selectedFolder = MutableLiveData<Folder>()

    init {
        var folderList = mutableListOf<Folder>()
        db.collection("folders").get()
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
                    foldersLiveData.postValue(folderList)//setValue
                }

    }

    fun getFolders(): MutableLiveData<List<Folder>> {
        return foldersLiveData
    }

    fun selectFolder(item: Folder) {
        selectedFolder.postValue(item)
    }

    fun insertFolder(id: String?, folderName: String?, notesNumber: Int?) {
        if (folderName == null) {
            return
        }

        //val image = dataSource.getRandomFlowerImageAsset()
        val newFolder = Folder(
                id,
                folderName,
                notesNumber
        )

    }
}
