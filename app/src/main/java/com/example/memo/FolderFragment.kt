package com.example.memo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot


private const val TAG = "Folder Fragment"

class FolderFragment : Fragment() {

    private val folderListViewModel : FolderListViewModel by activityViewModels()

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
                Log.d(TAG, "folderListViewModel.getFolders().observe ")
                folderAdapter.submitList(it as MutableList<Folder>)

            }
        })

        return view

    }

    private fun adapterOnClick(folder: Folder) {

        Log.d(TAG, "adapterOnClick $folder")
        val fragment = NoteFragment()
        folderListViewModel.selectFolder(folder)
        (activity as MainActivity?)?.startFragment(fragment)
    }

}
