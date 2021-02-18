package com.example.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val TAG = "EditNoteActivity"


class EditNoteActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note)

        mTopToolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        mBottomToolbar = findViewById(R.id.bottom_toolbar)
        setSupportActionBar(mBottomToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mTopToolbar?.inflateMenu(R.menu.top_menu)
        mBottomToolbar?.inflateMenu(R.menu.bottom_menu)
        menu?.setGroupVisible(R.id.create_folder_group, false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.addNote -> {
            Log.d(TAG, "addNote ")
            val intent = Intent(this, EditNoteActivity::class.java)
            this.startActivity(intent)
            true
        }
        R.id.backToPrevious -> {
            Log.d(TAG, "backToPrevious ")
            saveNotes()
            finish()
            true
        }
        R.id.saveNote -> {
            saveNotes()
            finish()
            true
        }
        R.id.openCamera -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }
        R.id.usePen -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveNotes(){
        val textInputLayout = findViewById<TextInputEditText>(R.id.note_content)
        val text: String = textInputLayout.text.toString()
        Log.d(TAG, "saveNote $text")
        /*
        * Log.d(TAG, "saveNote ")
            val note = hashMapOf("name" to "title", "folder_id" to 0, "contents" to "hello")//note_id
            db.collection("notes")
                .add(note)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }*/
    }

}