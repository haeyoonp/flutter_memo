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


private const val TAG = "Note Fragment"

class NoteFragment : Fragment() {

    private val noteListViewModel = NoteListViewModel()

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
        val noteAdapter = NoteAdapter{ note -> adapterOnClick(note)}
        val recyclerView: RecyclerView =  view.findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)

        if (recyclerView.getParent() != null) (recyclerView.getParent() as ViewGroup).removeView(recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = noteAdapter

        noteListViewModel.getNotes().observe(viewLifecycleOwner, Observer{
            it?.let {
                Log.d(TAG, "noteListViewModel.getFolders() ")
                noteAdapter.submitList(it as List<Note>)
            }
        })

        return view//recyclerView

    }

    private fun adapterOnClick(note: Note) {

        Log.d(TAG, "adapterOnClick clicked id ${id}")
        /*
        val intent = Intent(this, NoteFragment()::class.java)
        intent.putExtra(FOLDER_ID, folder.name)
        Log.d(TAG, "adapterOnClick ${folder.name}")
        startActivity(intent)
        */
    }


}