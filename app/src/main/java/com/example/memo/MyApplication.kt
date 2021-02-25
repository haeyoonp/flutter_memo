package com.example.memo

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import android.util.Log

private const val TAG = "MyApplication"

class MyApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository  by lazy { AppRepository(database.folderDao()) }
    var currentFolder: String? = "default"
    var currentNote: String? = null
}