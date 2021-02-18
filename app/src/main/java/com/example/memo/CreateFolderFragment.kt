package com.example.memo

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "CreateFolderFragment"

class CreateFolderFragment : DialogFragment() {

    private val folderListViewModel : FolderListViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val db = Firebase.firestore

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val dialogView: View = inflater.inflate(R.layout.create_folder, null)
            //builder.setView(inflater.inflate(R.layout.create_folder, null))
            builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        val name = (dialogView.findViewById(R.id.folderName) as EditText).text.toString()
                        //print name and put it in DB !
                        Log.i(TAG, "name of folder $name")
                        folderListViewModel.insertFolder(name, 0)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
