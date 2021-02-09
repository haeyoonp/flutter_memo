package com.example.memo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
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
    //val DATA_FOLDER: MutableMap<String,FolderModel> = mutableMapOf()
    //val LIST_FOLDER: MutableList<String> = mutableListOf()
    val items = mutableListOf<folderListViewItem>()


    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: CreateFolderFragment)
        fun onDialogNegativeClick(dialog: CreateFolderFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listview: ListView
        val adapter: ListViewAdapter

        // Adapter 생성
        adapter = ListViewAdapter()

        // 리스트뷰 참조 및 Adapter달기
        listview = findViewById<View>(R.id.folderListView) as ListView
        listview.adapter = adapter

        mTopToolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        mBottomToolbar = findViewById(R.id.bottom_toolbar)
        setSupportActionBar(mBottomToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        db.collection("folders").get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val folder = document.toObject(FolderModel::class.java)
                        adapter.addItem(folder.folder_id, folder.name, folder.number_notes)
                        Log.d(TAG, "${document.id} => ${document.data} , ${folder}  , ${folder.name}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }

        listview.setOnItemClickListener(OnItemClickListener { parent, view, position, id -> })


    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val listview = findViewById<View>(R.id.folderListView) as ListView
            val adapter: ListViewAdapter
            //listview.adapter = adapter
            //runOnUiThread { listadaptor.notifyDataSetChanged() }
        }
    }

    fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        Log.d(TAG, "clicked id ${id}")

        // Then you start a new Activity via Intent

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