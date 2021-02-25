package com.example.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import androidx.lifecycle.Observer
import com.example.memo.model.Note
import com.example.memo.model.NoteListViewModel
import java.util.*


private const val TAG = "EditNoteActivity"


class EditNoteActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null
    private val noteListViewModel = NoteListViewModel()
    private var currentNote: Note? =
        Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_note)
        val bundle = intent.extras
        val selectedFolder = bundle!!.getString("selectedFolder")
        val selectedNote = bundle!!.getString("selectedNote")

        noteListViewModel.selectNote(
            Note(
                selectedNote,
                null,
                selectedFolder,
                null,
                null
            )
        )
        Log.d(TAG, "noteListViewModel.selectNote $selectedFolder $selectedNote")

        val s = (this.application as MyApplication).currentFolder
        Log.d(TAG, "this.application as MyApplication $s")

        noteListViewModel.selectedNote.observe(this, Observer { item ->
            getNote(item)
            currentNote = item
        })

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
            saveNote()
            finish()
            true
        }
        R.id.saveNote -> {
            saveNote()
            finish()
            true
        }
        R.id.openCamera -> {
            true
        }
        R.id.usePen -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun getNote(note: Note){
        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        val dateView : TextView = findViewById(R.id.current_date)
        val contentView : TextInputEditText = findViewById(R.id.note_content)
        dateView.setText(currentDateTimeString)
        contentView.setText(note.contents)
    }

    private fun saveNote(){
        val currentDateTimeString = Date()//DateFormat.getDateTimeInstance().format(Date())
        val content : String = findViewById<TextView>(R.id.note_content).text.toString()
        currentNote?.contents = content
        currentNote?.name = content.split("\\s+".toRegex())[0]
        currentNote?.last_edit = currentDateTimeString
        Log.d(TAG, "saveNote ${currentNote}")

        noteListViewModel.saveNote(currentNote)


    }

}