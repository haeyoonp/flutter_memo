package com.example.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker


private const val TAG = "Folder Fragment"

class FolderFragment : Fragment() {

    private val folderListViewModel = FolderListViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView")

        val view = inflater.inflate(R.layout.recycler_view, container, false)
        val folderAdapter = FolderAdapter{ folder -> adapterOnClick(folder)}
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        //if (recyclerView.getParent() != null) (recyclerView.getParent() as ViewGroup).removeView(recyclerView)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = folderAdapter

        folderListViewModel.getFolders().observe(viewLifecycleOwner, Observer{
            it?.let {
                Log.d(TAG, "folderListViewModel observe $it ")
                //folderAdapter.updateFolderList(it as MutableList<Folder>)
                folderAdapter.submitList(it as MutableList<Folder>)
            }
        })

        return view//recyclerView

    }

    private fun adapterOnClick(folder: Folder) {

        Log.d(TAG, "adapterOnClick ")
        /*
        val fragmentManager = getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()

        val fragment = NoteFragment()
        fragmentTransaction.add(R.id.fragment_note, fragment)
        //fragmentTransaction.replace(R.id.recycler_view, fragment)
        fragmentTransaction.commit()

        val intent = Intent(this, NoteFragment()::class.java)
        intent.putExtra(FOLDER_ID, folder.name)
        Log.d(TAG, "adapterOnClick ${folder.name}")
        startActivity(intent)
        */
    }


}