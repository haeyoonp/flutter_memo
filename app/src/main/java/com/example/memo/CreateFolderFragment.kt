package com.example.memo

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.memo.model.Folder
import com.example.memo.model.FolderListViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "CreateFolderFragment"

class CreateFolderFragment : DialogFragment() {

    private val folderListViewModel : FolderListViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val db = Firebase.firestore

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            val dialogView: View = inflater.inflate(R.layout.create_folder, null)
            builder.setView(dialogView)
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        val name = (dialogView.findViewById(R.id.folderName) as EditText).text.toString()

                        //folderListViewModel.insertFolder(name, 0)

                        val addRunnable = Runnable {
                            val newFolder = Folder(null, name,0)
                            (activity as MainActivity?)?.folderListViewModel?.insert(newFolder)
                        }
                        val addThread = Thread(addRunnable)
                        addThread.start()
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
