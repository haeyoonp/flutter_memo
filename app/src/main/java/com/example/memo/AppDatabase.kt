package com.example.memo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.memo.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "AppDatabase"

@Database(entities = [Folder::class, NoteR::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao //abstract val folderDao: FolderDao
    abstract fun noteDao(): NoteDao //abstract val noteDao: NoteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                Log.d(TAG, "getDatabase $context")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memo_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "memo_db"
                    )
                        .addCallback(AppDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    Log.d(TAG, "onCreate $database")
                    populateDatabase(database.folderDao())
                }
            }
        }


        suspend fun populateDatabase(folderDao: FolderDao) {

            Log.d(TAG, "populateDatabase $folderDao")

            // Add sample words.
            val default = folderDao.findByName("default")
            if(default == null){
                var defaultFolder = Folder(0,"default", 0)
                folderDao.insertFolder(defaultFolder)
            }
        }
    }
}


/*

val db = Room.databaseBuilder(
    applicationContext,
    AppDatabase::class.java, "database-name"
).build()
*/