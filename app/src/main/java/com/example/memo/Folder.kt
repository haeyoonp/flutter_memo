package com.example.memo

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "Folder"

data class Folder (
        //var id: String? = null,
        var name: String? = null,
        var number_notes: Int? = null
)

fun folderList() : List<Folder>{

    val db = Firebase.firestore

    var folderList = mutableListOf<Folder>()
    db.collection("folders").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val folder = document.toObject(Folder::class.java)
                    folderList.add(folder)
                    Log.d(TAG, "${document.id} => ${document.data} , ${folder}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    Log.d(TAG, "folderList : ${folderList}")
    //return folderList
    return listOf(
            Folder(name="asdf", number_notes = 0),
            Folder("seee", number_notes = 2),
            Folder("qwer", number_notes = 1)
    )
}

class FolderListViewModel(val dataSource: MutableList<Folder>) : ViewModel() {

    val db = Firebase.firestore

    val foldersLiveData = dataSource

    /* If the name and description are present, create new Flower and add it to the datasource */
    fun insertFlower(id: String?, folderName: String?, notesNumber: Int?) {
        if (folderName == null) {
            return
        }

        //val image = dataSource.getRandomFlowerImageAsset()
        val newFlower = Folder(
                folderName,
                notesNumber
        )

    }
}