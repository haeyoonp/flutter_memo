package com.example.memo
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class EditNoteActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

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
            val intent = Intent(this, EditNoteActivity::class.java)
            this.startActivity(intent)
            true
        }
        R.id.backToPrevious -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            true
        }
        R.id.saveNote -> {
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
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

}