package com.example.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memo.model.FolderListViewModel
import com.example.memo.model.Note
import com.example.memo.model.NoteListViewModel
import com.example.memo.utils.NoteAdapter


private const val TAG = "Note Fragment"

class NoteFragment : Fragment() {

    private val noteListViewModel = NoteListViewModel()
    private val folderListViewModel : FolderListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.recycler_view, container, false)
        val noteAdapter =
            NoteAdapter { note -> adapterOnClick(note) }
        val recyclerView: RecyclerView =  view.findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = noteAdapter

        folderListViewModel.selectedFolder.observe(viewLifecycleOwner, Observer { item ->
            Log.d(TAG, "folderListViewModel selectedFolder ${item} ${item.uid}")
            //(activity as MainActivity?)?.currentFolder = item.id
            (requireActivity().application as MyApplication).currentFolder = item.folderName
            //noteListViewModel.filterNotes(item.uid)
        })

        noteListViewModel.getNotes().observe(viewLifecycleOwner, Observer{
            it?.let {
                Log.d(TAG, "noteListViewModel.getNotes() ")
                noteAdapter.submitList(it as MutableList<Note>)
            }
        })
        return view
    }

    private fun adapterOnClick(note: Note) {

        Log.d(TAG, "adapterOnClick ${note.note_id}")
        //noteListViewModel.selectNote(note)
        (activity as MainActivity?)?.openEditNoteActivity(note)
    }



}