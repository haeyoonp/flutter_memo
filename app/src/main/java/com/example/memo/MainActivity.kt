package com.example.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private var mTopToolbar: Toolbar? = null
    private var mBottomToolbar: Toolbar? = null
    internal lateinit var listener: NoticeDialogListener
    val db = Firebase.firestore
    /*
    private val flowersListViewModel by viewModels<FlowersListViewModel> {
        FlowersListViewModelFactory(this)
    }*/

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: CreateFolderFragment)
        fun onDialogNegativeClick(dialog: CreateFolderFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val folderAdapter = FolderAdapter{ folder -> adapterOnClick(folder)}
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager
        val folderList = folderList()

        layoutManager = LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.adapter = folderAdapter
        Log.d(TAG, "folderList : ${folderList}")
        folderAdapter.submitList(folderList)


        mTopToolbar = findViewById(R.id.top_toolbar)
        setSupportActionBar(mTopToolbar)

        mBottomToolbar = findViewById(R.id.bottom_toolbar)
        setSupportActionBar(mBottomToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)



        /*
        flowersListViewModel.flowersLiveData.observe(this, {
            it?.let {
                folderAdapter.submitList(it as MutableList<Folder>)
                //headerAdapter.updateFlowerCount(it.size)
            }
        })*/

    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(folder: Folder) {
        val intent = Intent(this, EditNoteActivity()::class.java)
        //intent.putExtra(FLOWER_ID, flower.id)
        startActivity(intent)
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
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newFlowerActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val flowerName = data.getStringExtra(FLOWER_NAME)
                val flowerDescription = data.getStringExtra(FLOWER_DESCRIPTION)

                flowersListViewModel.insertFlower(flowerName, flowerDescription)
            }
        }*/

    }
