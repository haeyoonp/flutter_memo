package com.example.memo

import android.util.Log
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

class FolderListViewModel() : ViewModel() {

    val db = Firebase.firestore

    private var foldersLiveData : MutableLiveData<List<Folder>> = MutableLiveData<List<Folder>>()

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
        Log.d(TAG, "getFolders() : $foldersLiveData")

        return foldersLiveData
    }

    fun insertFolder(id: String?, folderName: String?, notesNumber: Int?) {
        if (folderName == null) {
            return
        }

        //val image = dataSource.getRandomFlowerImageAsset()
        val newFlower = Folder(
                id,
                folderName,
                notesNumber
        )

    }
}

/*
fun folderList() : List<Folder>{

    val db = Firebase.firestore

    var folderList = mutableListOf<Folder>()
    db.collection("folders").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val folder = document.toObject(Folder::class.java)
                    folderList.add(folder)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    Log.d(TAG, "folderList : ${folderList}")
    return folderList
    /*return listOf(
            Folder(name="asdf", number_notes = 0),
            Folder("seee", number_notes = 2),
            Folder("qwer", number_notes = 1)
    )*/
}

*/