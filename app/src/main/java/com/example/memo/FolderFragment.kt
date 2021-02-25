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
import com.example.memo.model.Folder
import com.example.memo.model.FolderListViewModel
import com.example.memo.utils.FolderAdapter


private const val TAG = "Folder Fragment"

class FolderFragment : Fragment() {

    private val folderListViewModel : FolderListViewModel by activityViewModels()// = (activity as MainActivity?)?.folderListViewModel // FolderListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.recycler_view, container, false)
        val folderAdapter = FolderAdapter { folder ->
            adapterOnClick(folder)
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = folderAdapter
        Log.d(TAG, " !   $folderListViewModel ")
        if (folderListViewModel != null) {
            Log.d(TAG, "if (folderListViewModel != null)")
            folderListViewModel.foldersLiveData.observe(viewLifecycleOwner, Observer{
                it?.let {
                    Log.d(TAG, "folderListViewModel.getFolders().observe $it")
                    folderAdapter.submitList(it as MutableList<Folder>)

                }
            })
        }

        /*
        folderListViewModel.getFolders().observe(viewLifecycleOwner, Observer{
            it?.let {
                Log.d(TAG, "folderListViewModel.getFolders().observe ")
                folderAdapter.submitList(it as MutableList<Folder>)

            }
        })*/
        return view
    }

    private fun adapterOnClick(folder: Folder) {

        Log.d(TAG, "adapterOnClick $folder")
        val fragment = NoteFragment()
        if (folderListViewModel != null) {
            folderListViewModel.selectFolder(folder)
        }
        (activity as MainActivity?)?.startFragment(fragment)
    }

}
