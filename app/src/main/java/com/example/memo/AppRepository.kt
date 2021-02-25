package com.example.memo

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.memo.model.Folder
import com.example.memo.model.FolderDao
import kotlinx.coroutines.flow.Flow

private const val TAG = "AppRepository"

class AppRepository(private val folderDao: FolderDao) {

    val allFolders: Flow<List<Folder>> = folderDao.getAll()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(folder: Folder) {
        Log.d(TAG, "insert $folder")
        folderDao.insertFolder(folder)
    }


}