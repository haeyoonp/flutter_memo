package com.example.memo

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "DataSource"

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource(resources: Resources) {

    val db = Firebase.firestore

    private val initialFolderList = folderList()
    private val foldersLiveData = MutableLiveData<List<Folder>>()

    /* Adds flower to liveData and posts value. */
    fun addFolder(folder: Folder) {
        val currentList = foldersLiveData.value
        if (currentList == null) {
            foldersLiveData.postValue(listOf(folder))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, folder)
            foldersLiveData.postValue(updatedList)
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeFlower(flower: Folder) {
        val currentList = foldersLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(flower)
            foldersLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    /*
    fun getFlowerForId(id: Long): Folder? {
        foldersLiveData.value?.let { folders ->
            return folders.firstOrNull{ it.id == id}
        }
        return null
    }
    */

    fun getFlowerList(): LiveData<List<Folder>> {
        return foldersLiveData
    }

    /* Returns a random flower asset for flowers that are added. */
    /*
    fun getRandomFlowerImageAsset(): Int? {
        val randomNumber = (initialFolderList.indices).random()
        return initialFolderList[randomNumber].image
    }
    */

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}