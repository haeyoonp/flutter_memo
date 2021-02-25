package com.example.memo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder")
    fun getAll(): Flow<List<Folder>>

    @Query("SELECT * FROM folder WHERE uid IN (:folderIds)")
    fun loadAllByIds(folderIds: IntArray): Flow<List<Folder>>

    @Query("SELECT * FROM folder WHERE folder_name LIKE :first")
    fun findByName(first: String): Folder

    @Insert
    fun insertAll(vararg folders: Folder)

    @Insert
    suspend fun insertFolder(folder: Folder)

    @Update
    fun updateFolder(folders: Folder)

    @Delete
    fun delete(folder: Folder)
}