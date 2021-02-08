package com.example.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null
    internal lateinit var listener: NoticeDialogListener
    val db = Firebase.firestore
    val LIST_MENU = arrayOf("LIST1", "LIST2", "LIST3")

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: CreateFolderFragment)
        fun onDialogNegativeClick(dialog: CreateFolderFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTopToolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        mBottomToolbar = findViewById(R.id.bottom_toolbar)
        setSupportActionBar(mBottomToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU)

        val listview = findViewById(R.id.folderList) as ListView
        listview.setAdapter(adapter)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mTopToolbar?.inflateMenu(R.menu.top_menu)
        mBottomToolbar?.inflateMenu(R.menu.bottom_menu)

        menu?.setGroupVisible(R.id.edit_note_group, false)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.addNote -> {
            Log.i("MainActivity", "ADD NOTE ! ")
            val intent = Intent(this, EditNoteActivity::class.java)
            this.startActivity(intent)
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
}