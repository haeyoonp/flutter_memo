package com.example.memo


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker


private const val TAG = "Folder Fragment"

class FolderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Use the Kotlin extension in the fragment-ktx artifact

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.recycler_view, container, false)
        val recyclerview = view.findViewById(R.id.recycler_view) as RecyclerView // Add this

        if (recyclerview.getParent() != null) (recyclerview.getParent() as ViewGroup).removeView(recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = FolderAdapter{ folder -> adapterOnClick(folder)}
        return recyclerview

    }

    private fun adapterOnClick(folder: Folder) {

        Log.d(TAG, "clicked id ${id}")
        /*
        val intent = Intent(this, NoteFragment()::class.java)
        intent.putExtra(FOLDER_ID, folder.name)
        Log.d(TAG, "adapterOnClick ${folder.name}")
        startActivity(intent)
        */
    }


}