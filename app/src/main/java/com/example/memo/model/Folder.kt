package com.example.memo.model

import android.util.Log
import androidx.lifecycle.*
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memo.AppRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


private const val TAG = "Folder"

@Entity//(tableName = "folder")
data class Folder(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "folder_name") val folderName: String?,
    @ColumnInfo(name = "number_notes") val numberNotes: Int?
)



data class FolderA (
        var id: String? = null,
        var name: String? = null,
        var number_notes: Int? = null
)

class FolderListViewModel(private val repository: AppRepository) : ViewModel() {

    private val FOLDER_REF = Firebase.firestore.collection("folders")
    val foldersLiveData : LiveData<List<Folder>> = repository.allFolders.asLiveData()/*by lazy {
        MutableLiveData<List<Folder>>().also {
            //loadFolders()
        }
    }//= MutableLiveData<List<Folder>>()*/

    val selectedFolder = MutableLiveData<Folder>()

    fun insert(folder: Folder) = viewModelScope.launch {
        Log.d(TAG, "insert $folder")
        repository.insert(folder)
    }

    init{
        FOLDER_REF.addSnapshotListener{ snapshot, e ->
            Log.d(TAG, "FOLDER_REF.addSnapshotListener")
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                //loadFolders()
            }
        }
    }

    fun getFolders(): LiveData<List<Folder>> {
        return foldersLiveData
    }
    /*
    private fun loadFolders() {
        var folderList = mutableListOf<Folder>()

        FOLDER_REF.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val folder = document.toObject(Folder::class.java)
                        //folder.uid = document.id
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
    */
    fun selectFolder(item: Folder) {

        selectedFolder.postValue(item)
    }
    /*
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
    }*/

    fun deleteFolder(folder_id: String?) {
        Log.d(TAG, "deleteFolder $folder_id")
        //deleteNotes()
        //deleteFolder()

    }

}

class FolderViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d(TAG, "FolderViewModelFactory")
        if (modelClass.isAssignableFrom(FolderListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FolderListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
