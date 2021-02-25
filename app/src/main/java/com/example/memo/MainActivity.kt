package com.example.memo

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.memo.model.FolderListViewModel
import com.example.memo.model.FolderViewModelFactory
import com.example.memo.model.Note
import com.example.memo.model.NoteListViewModel


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null
    internal lateinit var listener: NoticeDialogListener
    val folderListViewModel : FolderListViewModel by viewModels {
        FolderViewModelFactory((application as MyApplication).repository)
    }
    private val noteListViewModel = NoteListViewModel()
    private val fragmentManager: FragmentManager = supportFragmentManager

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: CreateFolderFragment)
        fun onDialogNegativeClick(dialog: CreateFolderFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = FolderFragment()
        startFragment(fragment)

        mTopToolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        mBottomToolbar = findViewById(R.id.bottom_toolbar)
        setSupportActionBar(mBottomToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mTopToolbar?.inflateMenu(R.menu.top_menu)
        mBottomToolbar?.inflateMenu(R.menu.bottom_menu)

        menu?.setGroupVisible(R.id.edit_note_group, false)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.backToPrevious -> {
            backFragment()
            true
        }
        R.id.addNote -> {
            val note : Note =
                Note()
            var currentFolder = (this.application as MyApplication).currentFolder
            note.folder_id = currentFolder
            openEditNoteActivity(note)
            true
        }
        R.id.createFolder -> {
            val createFolder = CreateFolderFragment()
            createFolder.show(supportFragmentManager, "createFolder")
            true
        }
        R.id.openCamera -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

     fun startFragment(fragment: Fragment) {
         Log.i(TAG, "startFragment")
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_placeholder, fragment)
        transaction.addToBackStack(null);
        transaction.commit()
    }

    override fun onBackPressed() {

        if(fragmentManager.backStackEntryCount > 1){
            Log.i(TAG, "backStackEntryCount")
            fragmentManager.popBackStack()
        }else{
            finish()
        }
    }

    fun backFragment() {
        if(fragmentManager.backStackEntryCount > 1){
            Log.i(TAG, "backStackEntryCount")
            fragmentManager.popBackStack()
        }
    }

    fun openEditNoteActivity(note: Note?) {

        val folder_id = note?.folder_id
        val note_id = note?.note_id

        Log.d(TAG, "openEditNoteActivity ${folder_id} , ${note_id} ")
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra("selectedFolder", folder_id);
        intent.putExtra("selectedNote", note_id);
        this.startActivity(intent)
    }

}
